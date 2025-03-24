package booking_stay.booking_stay.couponeventV2.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponeventV2.domain.repository.CouponEventRedisRepository;
import booking_stay.booking_stay.couponeventV2.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponeventV2.service.CouponEventRedisService;
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
public class CouponEventRedisServiceImpl implements CouponEventRedisService {
    private final CouponEventRedisRepository couponEventRedisRepository;
    private final CouponEventRepository couponEventRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    public String couponEventProducer(CouponEventRequest request) {

        log.info(request.toString());
        int maxQuantity = getMaxQuantity(request);
        if (maxQuantity < 1)
            return "수량 소진";

        if (checkDuplicate(request))
            return "중복참여 불가";

        return addQueue(request);
    }

    @Override
    @Scheduled(fixedDelay = 1000)
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

    @Override
    @Transactional
    public String trunOnCouponEvent(Long id) {

        CouponEvent couponEvent = couponEventRepository.findCouponEventById(id).orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
        couponEvent.changeStatus(CouponEventStatus.DO);

        couponEventRedisRepository.setCouponMaxQuantity(id,couponEvent.getMaxQuantity());

        return "200";
    }

    @Override
    public String resetCouponEventCount(Long id) {
        CouponEvent couponEvent = couponEventRepository.findCouponEventById(id).orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));
        couponEventRedisRepository.resetCouponMaxQuantity(id, couponEvent.getMaxQuantity());

        return "200";
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

        checkCouponQuantity(request);

        MemberCoupon memberCouponSaved = memberCouponRepository.save(memberCoupon);
        log.info(memberCouponSaved.toString());
    }

    private int getMaxQuantity(CouponEventRequest request) {
        Integer couponQuantity = couponEventRedisRepository.getCouponEventMaxQuantity(request.getCouponEventId());
        if (couponQuantity!=null)
            return couponQuantity;


        couponQuantity = couponEventRepository.findById(request.getCouponId())
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT))
                .getMaxQuantity();
        couponEventRedisRepository.setCouponMaxQuantity(request.getCouponEventId(),couponQuantity);

        return couponQuantity;
    }

    private Boolean checkDuplicate(CouponEventRequest request) {
        return memberCouponRepository.existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId());
    }

    private void checkCouponQuantity(CouponEventRequest request) {
        Long couponQuantity = couponEventRedisRepository.decreaseCouponMaxQuantity(request.getCouponEventId());

        if (couponQuantity < 0)
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NO_REMAINING_COUPON);
    }

    private String addQueue(CouponEventRequest request) {

        Boolean addResult = couponEventRedisRepository.addQueue(request);
        if (!addResult)
            return "addQueue 실패";

        return "addQueue 성공";
    }
}