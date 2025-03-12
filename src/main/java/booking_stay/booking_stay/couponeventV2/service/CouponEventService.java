package booking_stay.booking_stay.couponeventV2.service;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventResponseDto;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventUpdateRequestDto;

public interface CouponEventService {
    CouponEvent createCouponEvent(CouponEventCreateRequestDto requestDto);

    Long updateCouponEvent(Long couponEventId, CouponEventUpdateRequestDto couponEventUpdateRequestDto);

    CouponEventResponseDto getCouponEvent(Long couponEventId);

    String couponEventPublisher(CouponEventRequest request);

    void couponEventConsumer();
}
