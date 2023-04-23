package interview.rest.api.model;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsResponse {
    String requesterId;
    List<AnalyticsJob> analyticsJob = new ArrayList<>();

    @Override
    public String toString() {
        return "AnalyticsResponse{" +
                "requesterId='" + requesterId + '\'' +
                ", analyticsJob=" + analyticsJob +
                '}';
    }

    public AnalyticsResponse(){

    }

    public AnalyticsResponse(String requesterId, List<AnalyticsJob> analyticsJob) {
        this.requesterId = requesterId;
        this.analyticsJob = analyticsJob;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public List<AnalyticsJob> getAnalyticsReport() {
        return analyticsJob;
    }

    public void setAnalyticsReport(List<AnalyticsJob> analyticsJob) {
        this.analyticsJob = analyticsJob;
    }

}

