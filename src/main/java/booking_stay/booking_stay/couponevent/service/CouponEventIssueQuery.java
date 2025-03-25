package booking_stay.booking_stay.couponevent.service;

import org.springframework.data.redis.core.ZSetOperations;

public interface CouponEventIssueQuery {

    Long getExistCouponEventCount();

    //redis로 한정되는게 문제
    ZSetOperations.TypedTuple<Object> popMinOne();

    Integer getCouponEventMaxQuantity(Long couponEventId);
}
