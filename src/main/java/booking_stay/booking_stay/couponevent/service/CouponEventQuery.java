package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;

public interface CouponEventQuery {

    CouponEvent getCouponEvent(Long couponId);

}
