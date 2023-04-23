package interview.rest.api.controller;

import interview.rest.api.service.AnalyticsService;
import interview.rest.api.model.AnalyticsRequest;
import interview.rest.api.model.AnalyticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "/analytics/job")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping(value = "/{id}")
    public AnalyticsResponse getJobStatusAndResults(@PathVariable String id)
    {
       return analyticsService.getAnalyticsReport(id);
    }

    @PostMapping
    public AnalyticsResponse createAnalyticsJob(@RequestBody AnalyticsRequest request){
        return analyticsService.createAnalyticsJob(request);
    }



}
