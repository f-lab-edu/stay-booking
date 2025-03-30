package booking_stay.booking_stay.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public abstract class ExecutorUtils {
    public static void printState(ExecutorService executorService) {

        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int poolSize = poolExecutor.getPoolSize();
            int activeCount = poolExecutor.getActiveCount();
            int queueSize = poolExecutor.getQueue().size();
            long completedTaskCount = poolExecutor.getCompletedTaskCount();

            log.info("poolSize =" + poolSize + "activeCount = " + activeCount + "queueSzie = " + queueSize + "completedTaskCount = " + completedTaskCount);
        }else {
            log.info(executorService.toString());
        }
    }
}
