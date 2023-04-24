package interview.rest.api.jobs;

import interview.rest.api.model.Workload;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mocking AWS SQS.
 */
@Component
public class AwsSQS {
    private final Set<Workload> workloads = new HashSet<>();

    public void publishToQueue(Workload workload){
        verifySize();
        workloads.add(workload);
    }
    public List<Workload> consume(String identifier){
        List<Workload> list = workloads.stream()
                .filter( workload -> identifier.equalsIgnoreCase(workload.getName()))
                .filter(workload ->  "IN_QUEUE".equalsIgnoreCase(workload.getStatus()) )
                .filter(workload ->  workload.getTries() < 3)
                .collect(Collectors.toList());
        return list;
    }
    public synchronized void remove(Workload workload){
        workloads.remove(workload);
    }
    private static void verifySize(){

    }
}
