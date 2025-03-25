package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.service.impl.CouponEventIssueServiceImpl;
import booking_stay.booking_stay.couponevent.service.impl.CouponEventServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventFacade {

    private final CouponEventIssueServiceImpl couponEventIssueService;
    private final CouponEventServiceImpl couponEventService;

    public String couponEventProducer(CouponEventRequest request) {
        log.info("Coupon Event Producer called");
        log.info(request.toString());

        int maxQuantity = getMaxQuantity(request);
        if (maxQuantity < 1)
            return "수량 소진";

        if (couponEventService.checkDuplicate(request))
            return "중복참여 불가";

        return addQueue(request);
    }

    public String turnOnCouponEvent(Long couponEventId) {

        CouponEvent couponEvent = couponEventService.updateCouponEventStatusDo(couponEventId);

        couponEventIssueService.setCouponMaxQuantity(couponEventId, couponEvent.getMaxQuantity());
        return "200";
    }

    public String resetCouponEventCount(Long couponEventId) {
        CouponEvent couponEvent = couponEventService.getCouponEvent(couponEventId);
        couponEventIssueService.resetCouponMaxQuantity(couponEventId, couponEvent.getMaxQuantity());

        return "200";
    }

    private int getMaxQuantity(CouponEventRequest request) {
        Integer couponQuantity = couponEventIssueService.getCouponEventMaxQuantity(request.getCouponEventId());
        if (couponQuantity!=null)
            return couponQuantity;


        couponQuantity = couponEventService.getCouponEvent(request.getCouponEventId()).getMaxQuantity();

        couponEventIssueService.setCouponMaxQuantity(request.getCouponEventId(),couponQuantity);

        return couponQuantity;
    }

    private String addQueue(CouponEventRequest request) {

        Boolean addResult = couponEventIssueService.addQueue(request);
        if (!addResult)
            return "addQueue 실패";

        return "addQueue 성공";
    }

}
