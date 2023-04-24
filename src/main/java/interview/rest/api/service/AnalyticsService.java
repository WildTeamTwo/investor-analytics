package interview.rest.api.service;

import interview.rest.api.model.AnalyticsJob;
import interview.rest.api.model.AnalyticsRequest;
import interview.rest.api.model.AnalyticsResponse;
import interview.rest.api.model.Workload;
import interview.rest.api.model.WorkloadRequest;
import interview.rest.api.model.WorkloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
public class AnalyticsService {

    @Autowired
    private RestTemplate restTemplate;

    public AnalyticsResponse getAnalyticsReport(String requestId){
        WorkloadResponse workloadResponse = restTemplate
                .getForObject("/analytics/workload/{requestId}",
                WorkloadResponse.class, requestId);

        AnalyticsResponse response = new AnalyticsResponse(workloadResponse.getRequestId(), to(workloadResponse.getWorkloads()));
        System.out.println("a response " + response);
        // return mockResponse(requestId);
        return response;

    }

    public AnalyticsResponse createAnalyticsJob(AnalyticsRequest request){
        String requestId = UUID.randomUUID().toString();
        HttpEntity<WorkloadRequest> workloadRequest = new HttpEntity<>(new WorkloadRequest(requestId, request.getReports() ));
        WorkloadResponse workloadResponse = restTemplate.postForObject("/analytics/workload/", workloadRequest, WorkloadResponse.class);
        AnalyticsResponse response = new AnalyticsResponse();
        response.setRequesterId(workloadResponse.getRequestId());
        response.setAnalyticsReport(to(workloadResponse.getWorkloads()));
        return response;
    }


    private List<AnalyticsJob> to(List<Workload> list){
        return emptyIfNull(list).stream()
                .map( workload -> new AnalyticsJob(  workload.getId(), workload.getName(), workload.getStatus(), workload.getResult(), workload.getCreated().toString(), workload.getUpdated().toString()) )
                .collect(Collectors.toList());
    }

}
