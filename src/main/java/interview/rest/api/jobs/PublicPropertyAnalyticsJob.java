

package interview.rest.api.jobs;

import interview.rest.api.model.Workload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class PublicPropertyAnalyticsJob {

    private static final int MAX_COUNT = 5;
    private static final int MAX_EXCEPTION_COUNT = 3;

    @Async
    public void poll(){
        try {
            checkQueue();
        } catch (Exception e){
            System.err.println(e.getStackTrace());
        }
        finally {
            System.out.println("Polling ends...");
        }
    }

    private void checkQueue() throws InterruptedException {
        int count = 0;
        int exceptionCount = 0;
        do {
            System.out.println("Public property job checking queue...");
            count++;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e){
                if (++exceptionCount > MAX_EXCEPTION_COUNT){
                    throw e;
                }
            }
        } while (count < MAX_COUNT );

    }


}



