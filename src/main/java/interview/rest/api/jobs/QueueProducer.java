package interview.rest.api.jobs;

import interview.rest.api.model.WorkloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Component
public class QueueProducer {

    private static int MAX_COUNT = 5;
    private static int MAX_EXCEPTIONS = 3;
    @Autowired
    private AwsSQS awsSqsQueue;

    @Async
    public void begin(){
        try {
            poll();
        } catch (Exception e){
            System.out.println("Polling ends");
        }
    }

    private void poll() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080").build();
        int count = 0;
        int exceptionCount = 0;
        do {
            try {
                count++;
                ResponseEntity<WorkloadResponse> responseEntity = restTemplate.getForEntity("/analytics/workload/ready-workloads", WorkloadResponse.class);
                WorkloadResponse response = responseEntity.getBody();
                if(response != null){
                    emptyIfNull(response.getWorkloads()).stream().forEach( w -> awsSqsQueue.publishToQueue(w));
                    continue;
                }
                Thread.sleep(7000);
            } catch (InterruptedException e){
                if (++exceptionCount > MAX_EXCEPTIONS){
                    throw e;
                }
            }
        } while (count < MAX_COUNT );
    }
}
