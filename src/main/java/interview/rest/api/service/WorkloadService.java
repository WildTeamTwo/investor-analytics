package interview.rest.api.service;

import interview.rest.api.jobs.CityTrafficAnalyticsJob;
import interview.rest.api.model.JobStatus;
import interview.rest.api.model.Workload;
import interview.rest.api.model.WorkloadRequest;
import interview.rest.api.model.WorkloadResponse;
import interview.rest.api.repository.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
public class WorkloadService {
    @Autowired
    private WorkloadRepository repository;
    @Autowired
    private CityTrafficAnalyticsJob jobA;

    public WorkloadResponse queueWorkload(WorkloadRequest request){
        List<Workload> workloads = emptyIfNull(request.getRequests())
                .stream()
                .map( name -> new Workload(request.getId(), name, JobStatus.IN_QUEUE.toString()))
                .collect(Collectors.toList());
        repository.saveAll(workloads);
        return new WorkloadResponse(request.getId(),workloads);
    }

    public WorkloadResponse updateStatus(String requestId, long workloadId, String status){
        Optional<Workload> workload = repository.findById(workloadId);
        workload.filter(w -> w.getRequestId().equalsIgnoreCase(requestId))
                .ifPresent(w -> {
                    w.setStatus(status); repository.save(w);
                });
        return new WorkloadResponse(requestId, Arrays.asList(workload.orElse(new Workload())));
    }

    public WorkloadResponse updateResults(String requestId, long workloadId, String result, String status) {
        Optional<Workload> workload = repository.findById(workloadId);
        if (workload.isPresent()) {
            int tries = workload.get().getTries();
            workload.get().setStatus(status);
            workload.get().setResult(result);
            workload.get().setTries(tries);
            repository.save(workload.get());
        }
        return new WorkloadResponse(requestId, Arrays.asList(workload.orElse(new Workload())));
    }
    public WorkloadResponse getStatus(String requestId) {
        List<Workload> workloads = repository.findByRequestId(requestId);
        return new WorkloadResponse(requestId, workloads);
    }

    public WorkloadResponse getWorkloadReadyForProcessing() {
        List<Workload> workloads = repository.findByStatus("IN_QUEUE");
        return new WorkloadResponse(UUID.randomUUID().toString(), workloads);
    }
}
