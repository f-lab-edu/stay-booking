package booking_stay.booking_stay.couponeventV2.service;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;

public interface CouponEventRedisService {

    String couponEventPublisher(CouponEventRequest request);

    void couponEventConsumer();
}
