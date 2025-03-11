package booking_stay.booking_stay.couponeventV2.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Component
public class CouponEventQueue {
    private static final Queue<CouponEventRequest> queue = new LinkedList<>();

    public void addRequest(CouponEventRequest request) {
        queue.offer(request);
    }

    public CouponEventRequest getRequest(){
        return queue.poll();
    }
}
