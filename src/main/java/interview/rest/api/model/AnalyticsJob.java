package interview.rest.api.model;

public class AnalyticsJob {

    private long workloadId;
    private String reportName;
    private String status;
    private String created;
    private String lastUpdated;
    private String result;

    public AnalyticsJob() {
    }

    public AnalyticsJob(long workloadId, String reportName, String status, String result, String created, String lastUpdated) {
        this.workloadId = workloadId;
        this.reportName = reportName;
        this.status = status;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.result = result;
    }

    public long getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(long workloadId) {
        this.workloadId = workloadId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    @Override
    public String toString() {
        return "AnalyticsJob{" +
                "workloadId=" + workloadId +
                ", reportName='" + reportName + '\'' +
                ", status='" + status + '\'' +
                ", created='" + created + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

}