package interview.rest.api.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Mock {

    public static AnalyticsResponse mockResponse(String requestId) {
        AnalyticsResponse response = new AnalyticsResponse();
        response.setRequesterId(requestId);
        response.setAnalyticsReport(Arrays.asList(new AnalyticsJob(121, "Report Name A", "DONE", "Result: Investment Grade A+", Instant.now().toString(), Instant.now().toString()),
                new AnalyticsJob(122, "Report Name B", "DONE", "Result: Investment Grade C-", Instant.now().toString(), Instant.now().toString()),
                new AnalyticsJob(123, "Report Name C", "DONE", "Result: Investment Grade A+", Instant.now().toString(), Instant.now().toString())
        ));

        return response;
    }

    public static Workload workload(){
        return new Workload(72, "f8f23ce2-a418-4258-8901-6e0ce3dd342c", "REPORT-A", "PROCESSING");
    }

    public static Integer randomInt() {
        return ThreadLocalRandom.current().nextInt(1, 10000);
    }
}
