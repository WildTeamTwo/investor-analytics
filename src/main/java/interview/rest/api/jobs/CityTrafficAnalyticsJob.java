package interview.rest.api.jobs;

import interview.rest.api.model.Workload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class CityTrafficAnalyticsJob {
    @Autowired
    private PubliCityTrafficAPI publiCityTrafficAPI;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AwsSQS queueConsumer;
    private static final int MAX_COUNT = 5;
    private static final int MAX_EXCEPTION_COUNT = 3;

    @Async
    public void poll(){
        try {
            checkQueue();
        } catch (Exception e){
            System.out.println("Polling ends...");
        }
    }

    private void checkQueue() throws InterruptedException {
        int count = 0;
        int exceptionCount = 0;
        do {
            System.out.println("City vehicle analytics job checking queue...");
            count++;
            try {
                List<Workload> workloads = queueConsumer.consume("Report Name A");
                System.out.println("Jobs in Queue " + workloads.size());
                workloads.stream().limit(2).forEach(workload ->  start(workload));
                Thread.sleep(10000);
            } catch (InterruptedException e){
                if (++exceptionCount > MAX_EXCEPTION_COUNT){
                    throw e;
                }
            }
        } while (count < MAX_COUNT );

    }
    private void start(Workload workload) {
        try {
            registerWorkloadIsStarted(workload);
            String analyticsResults = runAnalytics();
            persistWorkloadIsFinished(workload, analyticsResults);
        } catch (Exception e) {
            persistWorkloadHasFailed(workload, "FAILED");
            System.err.println(e.getStackTrace());
        }
        finally {
            queueConsumer.remove(workload);
        }
    }


    private void persistWorkloadSuccess(Workload workload) {
        String requestId = workload.getRequestId();
        long workloadId = workload.getId();
        String requestBody = workload.getResult();
        String uri = new StringBuilder().append("/analytics/workload/status/register-success/")
                .append(requestId)
                .append("/")
                .append(workloadId)
                .toString();
        persistWorkload(uri, requestBody);
    }

    private void persistWorkloadFail(Workload workload) {
        String requestId = workload.getRequestId();
        long workloadId = workload.getId();
        String requestBody = workload.getResult();
        String uri = new StringBuilder().append("/analytics/workload/status/register-fail/")
                .append(requestId)
                .append("/")
                .append(workloadId)
                .toString();
        persistWorkload(uri, requestBody);
    }

    private void persistWorkload(String uri, String requestBody) {
        restTemplate.put(uri, requestBody);
    }

    private void registerWorkloadIsStarted(Workload workload) {
        String requestId = workload.getRequestId();
        long workloadId = workload.getId();
        String results = workload.getResult();
        String uri = new StringBuilder().append("/analytics/workload/status/register-started/")
                .append(requestId)
                .append("/")
                .append(workloadId)
                .toString();
        restTemplate.put(uri, results);
    }

    private String runAnalytics() throws IOException, URISyntaxException {
        System.out.print("City Traffic Analytics running...");
        String jsonResults = publiCityTrafficAPI.downloadParkingData();
        //previewData(jsonResults);
        sleep();
        System.out.println("Analytics finished.\n");
        return "Here are Analytics results: Latest Analytics reports gives you an A+ Investment Grade.";
    }

    private void sleep() {
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void persistWorkloadIsFinished(Workload workload, String results) {
        updateWorkload(workload, results, "DONE");
        persistWorkloadSuccess(workload);
    }

    private void persistWorkloadHasFailed(Workload workload, String results) {
        updateWorkload(workload, results, "FAILED");
        persistWorkloadFail(workload);
    }

    private void updateWorkload(Workload workload, String results, String status) {
        workload.setStatus(status);
        workload.setResult(results);
    }

    private void previewData(String json) {
        try {
            System.out.println("Traffic Data Preview: \n" + json.substring(0, Math.min(900, json.length())).concat("..."));
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }
}



