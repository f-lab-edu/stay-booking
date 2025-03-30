package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventFacade {

    private final CouponEventIssueService couponEventIssueService;
    private final CouponEventQuery couponEventQuery;
    private final CouponEventCommand couponEventCommand;

    public CouponEvent createCouponEvent(CouponEventCreateRequestDto couponEventCreateRequestDto) {
        return couponEventCommand.createCouponEvent(couponEventCreateRequestDto);
    }

    public Long updateCouponEvent(Long couponEventId, CouponEventUpdateRequestDto couponEventUpdateRequestDto) {
        return couponEventCommand.updateCouponEvent(couponEventId, couponEventUpdateRequestDto);
    }

    public CouponEvent updateCouponEventStatusFinish(Long couponEventId) {
        return couponEventCommand.updateCouponEventStatusFinish(couponEventId);
    }

    public CouponEvent getCouponEvent(Long couponEventId) {
        return couponEventQuery.getCouponEvent(couponEventId);
    }
    
    public String couponEventProducer(CouponEventRequest request) {
        return couponEventIssueService.couponEventProducer(request);
    }

    public String turnOnCouponEvent(Long couponEventId) {
        return couponEventIssueService.turnOnCouponEvent(couponEventId);
    }

    public String resetCouponEventCount(Long couponEventId) {
        couponEventIssueService.resetCouponEventCount(couponEventId);
        return "200";
    }

}
