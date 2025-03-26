package booking_stay.booking_stay.couponevent.service.impl;


import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRedisRepository;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.service.CouponEventIssueService;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventIssueServiceImpl implements CouponEventIssueService {
    private final CouponEventRedisRepository couponEventRedisRepository;
    private final CouponEventRepository couponEventRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    public String couponEventProducer(CouponEventRequest couponEventRequest) {
        log.info("Coupon Event Producer called");
        log.info(couponEventRequest.toString());

        int maxQuantity = getMaxQuantity(couponEventRequest);
        if (maxQuantity < 1)
            return "수량 소진";

        if (checkDuplicate(couponEventRequest))
            return "중복참여 불가";

        return addQueue(couponEventRequest);
    }

    @Override
    public String turnOnCouponEvent(Long couponEventId) {
        CouponEvent couponEvent = couponEventRepository.findCouponEventById(couponEventId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
        couponEvent.changeStatusDo();

        couponEventRedisRepository.setCouponMaxQuantity(couponEventId,couponEvent.getMaxQuantity());

        return "200";
    }

    @Override
    public void resetCouponEventCount(Long couponEventId) {
        CouponEvent couponEvent = couponEventRepository.findCouponEventById(couponEventId).orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
        couponEventRedisRepository.resetCouponMaxQuantity(couponEventId, couponEvent.getMaxQuantity());
    }

    private int getMaxQuantity(CouponEventRequest request) {
        Integer couponQuantity = couponEventRedisRepository.getCouponEventMaxQuantity(request.getCouponEventId());
        if (couponQuantity==null)
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT);

        return couponQuantity;
    }

    private String addQueue(CouponEventRequest request) {

        Boolean addResult = couponEventRedisRepository.addQueue(request);
        if (!addResult)
            return "addQueue 실패";

        return "addQueue 성공";
    }

    private Boolean checkDuplicate(CouponEventRequest request) {
        return memberCouponRepository.existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId());
    }
}
