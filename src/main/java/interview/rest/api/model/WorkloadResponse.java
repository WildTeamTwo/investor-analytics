package interview.rest.api.model;

import java.util.List;

public class WorkloadResponse {
    private String requestId;
    private List<Workload> workloads;

    public WorkloadResponse(){

    }
    public WorkloadResponse(String requestId, List<Workload> workloads){
        this.requestId = requestId;
        this.workloads = workloads;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Workload> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(List<Workload> workloads) {
        this.workloads = workloads;
    }

    @Override
    public String toString() {
        return "WorkloadResponse{" +
                "requestId='" + requestId + '\'' +
                ", workloads=" + workloads +
                '}';
    }
}
