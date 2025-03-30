package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;

public interface CouponEventCommand {
    CouponEvent createCouponEvent(CouponEventCreateRequestDto requestDto);

    Long updateCouponEvent(Long couponEventId, CouponEventUpdateRequestDto requestDto);

    CouponEvent updateCouponEventStatusFinish(Long id);
}
