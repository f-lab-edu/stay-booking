package booking_stay.booking_stay.couponeventV2.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.domain.repository.CouponEventRedisRepository;
import booking_stay.booking_stay.couponeventV2.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponeventV2.service.CouponEventRedisService;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventRedisServiceImpl implements CouponEventRedisService {

    private final CouponEventRedisRepository couponEventRedisRepository;
    private final CouponEventRepository couponEventRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    public String couponEventPublisher(CouponEventRequest request) {

        return "";
    }

    @Override
    @Scheduled(fixedDelay = 500)
    public void couponEventConsumer() {
        log.info("counsumer is working");

//        if(발행할 쿠폰이 없으면)
//            return ;

//        쿠폰 발행
        issueCoupon();

//        네트워크확인은안하는지?

    }

    private void issueCoupon() {

    }

    private int getMaxQuantity(CouponEventRequest request) {
        Integer couponMaxCount = couponEventRedisRepository.getCouponEventMaxQuantity(request.getCouponEventId());
        if (couponMaxCount!=null)

        if (couponEventRedisRepository.getCouponEventMaxQuantity(request.getCouponEventId())==null){
            int maxQuantity = couponEventRepository.findById(request.getCouponId())
                    .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT))
                    .getMaxQuantity();
            couponEventRedisRepository.setCouponMaxQuantity(request.getCouponEventId(),maxQuantity);
        }

        return couponEventRedisRepository.getCouponEventMaxQuantity(request.getCouponEventId());
    }
}
