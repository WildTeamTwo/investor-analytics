package interview.rest.api.repository;

import interview.rest.api.model.Workload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkloadRepository extends CrudRepository<Workload, Long> {

    List<Workload> findByRequestId(String requestId);
    List<Workload> findByStatus(String status);
}
