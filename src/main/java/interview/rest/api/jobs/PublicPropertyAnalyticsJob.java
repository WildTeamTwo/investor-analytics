

package interview.rest.api.jobs;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PublicPropertyAnalyticsJob {

    private static final int MAX_COUNT = 1;
    private static final int MAX_EXCEPTION_COUNT = 1;

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



