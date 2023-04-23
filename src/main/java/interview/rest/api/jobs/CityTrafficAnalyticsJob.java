package interview.rest.api.jobs;

import interview.rest.api.model.Mock;
import interview.rest.api.model.Workload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class CityTrafficAnalyticsJob {
    @Autowired
    private PubliCityTrafficAPI publiCityTrafficAPI;
    @Autowired
    private RestTemplate restTemplate;

    public void start() {
        Workload workload = Mock.workload();
        try {
            registerWorkloadIsStarted(workload);
            String analyticsResults = runAnalytics();
            persistWorkloadIsFinished(workload, analyticsResults);
        } catch (Exception e) {
            persistWorkloadHasFailed(workload, "FAILED");
            System.err.println(e);
            System.err.println(e.getStackTrace());
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
        System.out.println("Analytics running...");
        String jsonResults = publiCityTrafficAPI.downloadParkingData();
        previewData(jsonResults);
        sleep();
        System.out.println("Analytics finished.");
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



