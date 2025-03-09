package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;

public interface CouponEventService {
    String couponEventPublisher(CouponEventRequest request);

    void couponEventConsumer();
}
