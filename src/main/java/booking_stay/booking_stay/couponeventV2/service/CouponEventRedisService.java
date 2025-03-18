package booking_stay.booking_stay.couponeventV2.service;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;

public interface CouponEventRedisService {

    String couponEventProducer(CouponEventRequest request);

    void couponEventConsumer();
}
