
Sample Rest API for Interview Demo.


Purpose
The Job API talks to the Workload API.  The workload API represents a series of analytics jobs (batch jobs) that have been started by the Analytics job.  For the purposes of the demo, imagine that the Job API is the API Gateway (public subnet) and the Workload API is on a private subnet because it performs expensive operations.

The Analytics Job's functions 
1. Puts multiple analytics request on the workload queue (jobs run asynchronously). 
2. Check the real-time status of the job (in queue, started, done, failed)
3. Check the final results of the job 

The Worloads functions
1. Store analytics request as jobs in the Workloads Table.
2. Maintains their status so that job executors can distinguish jobs are ready for execution.
3. Publish Events to subscribers
4. Provide consumers the ability to check the status and final results of a job.

Project Requirements
* Spring Boot 2.7.x
* MySQL 8

Two Rest APIs
1. http://localhost:8080/analytics/job
2. http://localhost:8080//analytics/workload

