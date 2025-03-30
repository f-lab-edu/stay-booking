package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;

public interface CouponEventIssueService {

    String couponEventProducer(CouponEventRequest couponEventRequest);

    String turnOnCouponEvent(Long couponEventId);

    void resetCouponEventCount(Long couponEventId);
}
