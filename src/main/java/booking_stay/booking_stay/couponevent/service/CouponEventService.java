package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventResponseDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;

public interface CouponEventService {

    CouponEvent updateCouponEventStatusDo(Long id);

    CouponEvent getCouponEvent(Long couponId);

    Boolean checkDuplicate(CouponEventRequest request);
}
