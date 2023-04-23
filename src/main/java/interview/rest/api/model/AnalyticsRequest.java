package interview.rest.api.model;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsRequest {
    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    List<String> reports = new ArrayList<String>();

}
