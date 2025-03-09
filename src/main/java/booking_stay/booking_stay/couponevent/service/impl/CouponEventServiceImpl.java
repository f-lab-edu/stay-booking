package booking_stay.booking_stay.couponevent.service.impl;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventQueue;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.service.CouponEventService;
import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventServiceImpl implements CouponEventService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponEventQueue couponEventQueue;
    private static final AtomicInteger issuedCoupon = new AtomicInteger(0);

    @Override
    public String couponEventPublisher(CouponEventRequest request) {
//        if (!checkDuplicate(request))
//            return "이미 참여하셨습니다.";
        couponEventQueue.addRequest(request);

        return "대기열 참여";
    }

    @Override
    @Scheduled(fixedDelay = 1000)
//    @SchedulerLock
    public void couponEventConsumer() {
        log.info("consumer is working");

        if (issuedCoupon.intValue()>=CouponEventQueue.MAX_SIZE) {
            log.info("쿠폰이 모두 소진되었음");
            return;
        }

        issueCoupon();
    }

    private Boolean checkDuplicate(CouponEventRequest request) {
        return memberCouponRepository.existsByUserIdAndCouponId(request.getUserId(), request.getCouponId());
    }

    private void issueCoupon() {
        CouponEventRequest request = couponEventQueue.getRequest();

        if(request==null)
            return;

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .userId(request.getUserId())
                .couponId(request.getCouponId())
                .build();

        MemberCoupon memberCouponReuslt = memberCouponRepository.save(memberCoupon);
        log.info(memberCouponReuslt.toString());

        issuedCoupon.incrementAndGet();
        log.info(String.valueOf(issuedCoupon));
    }
}
