package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;

public interface CouponEventRedisService {

    String couponEventProducer(CouponEventRequest request);

    void couponEventConsumer();

    String trunOnCouponEvent(Long id);

    String resetCouponEventCount(Long id);
}