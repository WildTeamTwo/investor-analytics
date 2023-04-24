
# Sample Rest API for Interview Demo.

# Design Diagram
https://excalidraw.com/#json=Rqf40VFhdgnAa9DBa35lW,qAXsk4p3FiQVium7UvV1qQ

# Purpose
The Job API talks to the Workload API.  The workload API represents a series of analytics jobs (batch jobs) that have been started by the Analytics job.  For the purposes of the demo, imagine that the Job API is the API Gateway (public subnet) and the Workload API is on a private subnet because it performs expensive operations.

## The Job's functions 
1. Puts multiple analytics request on the workload queue (jobs run asynchronously). 
2. Check the real-time status of the job (in queue, started, done, failed)
3. Check the final results of the job 

## The Workload's functions
1. Store analytics request as jobs in the Workloads Table.
2. Maintains their status so that job executors can distinguish jobs are ready for execution.
3. Publish Events to subscribers
4. Provide consumers the ability to check the status and final results of a job.

## Run instructions
mvn spring-boot:run

## Requirements
* Spring Boot 2.7.x
* Maven 3.*
* MySQL 8
* Java 11

## Two Rest APIs
1. http://localhost:8080/analytics/job
2. http://localhost:8080//analytics/workload

## Design  
The Design allows for many consumers (analytics jobs and event consumers) to run simultaneously without chaining to one another.
The separation also grants the ability to notify of more events. For example, let's say the sales department wants to be notified by email of every analytics request in they're respective region.
We could snap that functionality in without interfering with existing code. The separated design with polling responsibilities also allows for test that will not bloat because the application context is not rapidly growing a tangled web of dependecies.     
    

## Design Considerations/Improvements
* The AWS queue is mocked. I labeled as AwsQueue because that would be my preference.  A managed service would remove so many error cases.
* Queue was chosen because a managed service queue (not the one I used but like AWS/Kafka/MQ) is limiting the awareness and dependency of components.
* Queue was chosen over SNS (pub/sub) for its resilience. A managed queue has built in retry mechanism. But pub/sub is intended to fire and forget.
* The separation and the queue also gives scalability. The consumers/jobs can scale vertically or horizontally. They could be on different machines, networks, namespace (assuming security allows).               
* The logic for polling for workloads is for demo purposes.  This is not ideal for production. Using the java concurrent is best in that situation.
* @Async @EventListener are  also not ideal for triggering polling. But it was simpler for demo purpose.
* There is no logic put in to prevent multiple consumers of the same type executing the same workload at the same time.  This could be resolved with distributing a temporary key that blocks other consumers until it expires.   
     
# Sample Requests
## Post requests to run analytics job 
curl --location 'http://localhost:8080/analytics/job' \
--header 'Content-Type: application/json' \
--data '{
    "reports": [
        "Report Name A",
        "Report Name B",
        "Report Name C"
    ]
}' 

## Post Response 
{
    "requesterId": "7c716909-88c6-40c4-83ce-29802593cb05",
    "analyticsReport": [
        {
            "workloadId": 205,
            "reportName": "Report Name A",
            "status": "IN_QUEUE",
            "created": "2023-04-23T22:57:39.391924",
            "lastUpdated": "2023-04-23T22:57:39.391924",
            "result": ""
        },
        {
            "workloadId": 206,
            "reportName": "Report Name B",
            "status": "IN_QUEUE",
            "created": "2023-04-23T22:57:39.391941",
            "lastUpdated": "2023-04-23T22:57:39.391941",
            "result": ""
        },
        {
            "workloadId": 207,
            "reportName": "Report Name C",
            "status": "IN_QUEUE",
            "created": "2023-04-23T22:57:39.391945",
            "lastUpdated": "2023-04-23T22:57:39.391945",
            "result": ""
        }
    ]
}


## Get's status of running job  (requestId is in the response of post request above. use here )
curl --location 'http://localhost:8080/analytics/job/{requestId}'

## improvements 
* Rest Docs to document the purpose of each job in a easily displayable way
* Make writes to the database Transacational. 