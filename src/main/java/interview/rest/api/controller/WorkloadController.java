package interview.rest.api.controller;

import interview.rest.api.model.WorkloadRequest;
import interview.rest.api.model.WorkloadResponse;
import interview.rest.api.service.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/analytics/workload")
public class WorkloadController {

    @Autowired
    private WorkloadService workloadService;
    @Value("api.port")
    private String port;

    @PostMapping
    @ResponseBody
    public WorkloadResponse queueWorkload(@RequestBody WorkloadRequest request) throws Exception{
        //System.out.println("queueWorkload " + request.toString() );
        return workloadService.queueWorkload(request);
    }

    @GetMapping("/{requestId}")
    @ResponseBody
    public WorkloadResponse getWorkloadStatus(@PathVariable String requestId){
        return workloadService.getStatus(requestId);
    }

    @GetMapping("/ready-workloads")
    @ResponseBody
    public WorkloadResponse getWorkloadReadyForProcessing(){
        return workloadService.getWorkloadReadyForProcessing();
    }
    @PutMapping("/status/{requestId}/{workloadId}/{status}")
    public WorkloadResponse updateStatus(@PathVariable String requestId, @PathVariable long workloadId, @PathVariable String status){
        return workloadService.updateStatus(requestId, workloadId, status);
    }

    @PutMapping("/status/register-started/{requestId}/{workloadId}")
    public WorkloadResponse updateWorkloadStarted(@PathVariable String requestId, @PathVariable long workloadId){
        return workloadService.updateStatus(requestId, workloadId, "STARTED");
    }

    @PutMapping("/status/register-success/{requestId}/{workloadId}")
    public WorkloadResponse updateResultsSuccess(@PathVariable String requestId, @PathVariable long workloadId, @RequestBody String results){
        return workloadService.updateResults(requestId, workloadId, results,"DONE");
    }
    @PutMapping("/status/register-fail/{requestId}/{workloadId}")
    public WorkloadResponse updateResultsFail(@PathVariable String requestId, @PathVariable long workloadId, @RequestBody String results){
        return workloadService.updateResults(requestId, workloadId, results,"FAIL");
    }

    @PutMapping("/increment-tries-by-one/{requestId}/{workloadId}")
    public WorkloadResponse incrementTriesByOne(String requestId, String workloadId){
        return new WorkloadResponse();
    }

    @PutMapping("/update-last-ran-date/{requestId}/{workloadId}")
    public WorkloadResponse updateLastRanDate(String requestId, String workloadId){
        return new WorkloadResponse();
    }

}
