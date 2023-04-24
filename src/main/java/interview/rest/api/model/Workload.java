package interview.rest.api.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Workload {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name= "request_id", updatable = false)
    private String requestId;
    @Column(name = "report_name", updatable = false)
    private String name;
    @Column (updatable = true)
    private String status;
    @Column (updatable = true)
    private int tries = 0;
    @Column
    private LocalDateTime created = LocalDateTime.now();
    @Column
    private LocalDateTime updated = created;
    @Column
    private String result = "";

    public Workload(){

    }
    public Workload(String requestId, String name, String status){
        this.requestId = requestId;
        this.name = name;
        this.status = status;
    }

    public Workload(long id, String requestId, String name, String status){
        this.id = id;
        this.requestId = requestId;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Workload{" +
                "id=" + id +
                ", requestId='" + requestId + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", tries=" + tries +
                ", created=" + created +
                ", updated=" + updated +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workload workload = (Workload) o;
        return id == workload.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
