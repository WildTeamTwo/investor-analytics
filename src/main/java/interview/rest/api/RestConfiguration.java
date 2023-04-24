package interview.rest.api;

import interview.rest.api.jobs.CityTrafficAnalyticsJob;
import interview.rest.api.jobs.QueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class RestConfiguration {
    @Autowired
    private QueueProducer queueProducer;
    @Autowired
    private CityTrafficAnalyticsJob cityTrafficAnalyticsJob;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws InterruptedException {
        queueProducer.begin();
        cityTrafficAnalyticsJob.poll();
    }
}
