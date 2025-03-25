package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;

public interface CouponEventIssueCommand {

    Boolean addQueue(CouponEventRequest request);

    void resetCouponMaxQuantity(Long couponEventId, Integer quantity);

    Boolean setCouponMaxQuantity(Long couponEventId , Integer quantity);

    Long decreaseCouponMaxQuantity(Long couponEventId);
}
