package booking_stay.booking_stay.couponevent.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Component
public class CouponEventQueue {

    public static final int MAX_SIZE = 10000;

    private static final Queue<CouponEventRequest> queue = new LinkedList<>();

    public void addRequest(CouponEventRequest request) {
        if (queue.size()<MAX_SIZE)
            queue.offer(request);
        else
            log.info("full");
    }

    public CouponEventRequest getRequest(){
        return queue.poll();
    }
}
