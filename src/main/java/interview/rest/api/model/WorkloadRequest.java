package interview.rest.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkloadRequest {
    private String id;
    private List<String> requests = new ArrayList<String>();

    public WorkloadRequest(){
    }
    public WorkloadRequest(String id, List<String> requests){
        this.id = id;
        this.requests = requests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "WorkloadRequest{" +
                "requestId='" + id + '\'' +
                ", requests=" + Arrays.toString(requests.toArray()) +
                '}';
    }
}
