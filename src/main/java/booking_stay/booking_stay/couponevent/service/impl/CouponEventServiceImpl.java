package booking_stay.booking_stay.couponevent.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;
import booking_stay.booking_stay.couponevent.service.CouponEventCommand;
import booking_stay.booking_stay.couponevent.service.CouponEventQuery;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventServiceImpl implements CouponEventQuery, CouponEventCommand {

    private final CouponEventRepository couponEventRepository;

    @Override
    @Transactional
    public CouponEvent createCouponEvent(CouponEventCreateRequestDto requestDto) {
        return couponEventRepository.save(requestDto.toEntity());
    }

    @Override
    @Transactional
    public Long updateCouponEvent(Long couponEventId, CouponEventUpdateRequestDto requestDto) {
        CouponEvent couponEvent = couponEventRepository.findById(couponEventId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));

        couponEvent.update(couponEvent.getStatus(), requestDto);

        return couponEventId;
    }

    @Override
    @Transactional
    public CouponEvent updateCouponEventStatusFinish(Long id) {

        CouponEvent couponEvent = couponEventRepository.findCouponEventById(id).orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
        couponEvent.changeStatusFinish();

        return couponEvent;
    }

    @Override
    @Transactional(readOnly = true)
    public CouponEvent getCouponEvent(Long couponId) {
        return couponEventRepository.findById(couponId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
    }
}
