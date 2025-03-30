package booking_stay.booking_stay.couponevent.service;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRedisRepository;
import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventConsumer {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponEventRedisRepository couponEventRedisRepository;

    @Scheduled(fixedDelay = 10)
    @Transactional
    public void couponEventConsumer() {
        log.info("counsumer is working");
        log.info("");

        if (couponEventRedisRepository.getExistCouponEventCount() < 1){
            log.info("진행중인 쿠폰이벤트없음");
            return;
        }

        issueCoupon();
        log.info("coupon issued");
    }

    private void issueCoupon() {
//        대기열 가져오기
        ZSetOperations.TypedTuple<Object> typedTuple = couponEventRedisRepository.popMinOne();
        Object object = typedTuple.getValue();
        if (!(object instanceof CouponEventRequest)) {
            log.warn("타입 불일치, object: {}", Optional.ofNullable(object).getClass().getName());
            return;
        }

        issueCoupon((CouponEventRequest) object);
    }


    private void issueCoupon(CouponEventRequest request){

        if (checkDuplicate(request))
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_ISSUED_USER);

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .couponId(request.getCouponId())
                .userId(request.getUserId())
                .couponEventId(request.getCouponEventId())
                .build();

        try{
            checkCouponQuantity(request);
            memberCouponRepository.save(memberCoupon);
        }catch (Exception e){
            couponEventRedisRepository.increaseCouponMaxQuantity(request.getCouponEventId());
            log.error(e.getMessage());
        }
//        실패해도 dcrease된다 이것도 통합테스트로 만들고 로직도 구현해야한다
//        동시성 늘려가면서 테스트 그리고 늘려갓을때 속도도 점점더 빨라지는 테스트가 케이스별로 쭉쭉
    }

    private Boolean checkDuplicate(CouponEventRequest request) {
        return memberCouponRepository.existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId());
    }

    private void checkCouponQuantity(CouponEventRequest request) {
        Long couponQuantity = couponEventRedisRepository.decreaseCouponMaxQuantity(request.getCouponEventId());

        if (couponQuantity < 0)
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NO_REMAINING_COUPON);
    }
}
