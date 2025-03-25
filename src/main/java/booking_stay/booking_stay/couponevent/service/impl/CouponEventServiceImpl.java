package booking_stay.booking_stay.couponevent.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventQueue;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventResponseDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;
import booking_stay.booking_stay.couponevent.service.CouponEventService;
import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventServiceImpl implements CouponEventService {

    private final CouponEventRepository couponEventRepository;
    private final CouponEventQueue couponEventQueue;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    @Transactional
    public CouponEvent updateCouponEventStatusDo (Long id) {

        CouponEvent couponEvent = couponEventRepository.findCouponEventById(id).orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
        couponEvent.changeStatus(CouponEventStatus.DO);

        return couponEvent;
    }

    @Override
    public CouponEvent getCouponEvent(Long couponId) {
        return couponEventRepository.findById(couponId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
    }

    @Override
    public Boolean checkDuplicate(CouponEventRequest request) {
        return memberCouponRepository.existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId());
    }
}
