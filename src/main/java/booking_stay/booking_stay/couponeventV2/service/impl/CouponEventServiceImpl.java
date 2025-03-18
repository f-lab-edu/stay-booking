package booking_stay.booking_stay.couponeventV2.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventQueue;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventResponseDto;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventUpdateRequestDto;
import booking_stay.booking_stay.couponeventV2.service.CouponEventService;
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
    private final Map<Long, Integer> maxQuantityMap = new ConcurrentHashMap<>();
    private final Map<Long, Integer> issuedCouponCount = new ConcurrentHashMap<>();

    @Override
    public CouponEvent createCouponEvent(CouponEventCreateRequestDto requestDto) {
        return couponEventRepository.save(requestDto.toEntity());
    }

    @Transactional
    @Override
    public Long updateCouponEvent(Long couponEventId, CouponEventUpdateRequestDto requestDto) {

        CouponEvent couponEvent = couponEventRepository.findById(couponEventId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT));

        couponEvent.update(couponEvent.getStatus(), requestDto);

        return couponEventId;
    }

    @Override
    public CouponEventResponseDto getCouponEvent(Long couponEventId) {
        return couponEventRepository.findById(couponEventId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT))
                .toResponseDto();
    }

    @Transactional
    @Override
    public String couponEventPublisher(CouponEventRequest request) {
        int maxQuantity = getMaxQuantity(request);

        if (checkDuplicate(request))
            return "중복참여 불가";

        if (memberCouponRepository.countMemberCouponByCouponEventId(request.getCouponEventId())>=maxQuantity)
            return "수량 소진";

        couponEventQueue.addRequest(request);

        return "참여 완료";
    }

    @Override
//    @Scheduled(fixedDelay = 500)
    public void couponEventConsumer() {
        log.info("consumer is working");

        CouponEventRequest request = couponEventQueue.getRequest();
        if (request==null){
            log.info("대기중인 요청 없음");
            return;
        }

        int issuedCouponCount = getIssuedCouponCount(request.getCouponEventId());

        if (issuedCouponCount >= getMaxQuantity(request)) {
            log.info("수량 소진");
            return;
        }

        issueCoupon(request);
        maxQuantityMap.put(request.getCouponEventId(), issuedCouponCount+1);

    }

    private void issueCoupon(CouponEventRequest request){
        MemberCoupon memberCoupon = MemberCoupon.builder()
                .couponId(request.getCouponId())
                .userId(request.getUserId())
                .couponEventId(request.getCouponEventId())
                .build();

        MemberCoupon memberCouponSaved = memberCouponRepository.save(memberCoupon);
        log.info(memberCouponSaved.toString());


    }

    private Boolean checkDuplicate(CouponEventRequest request) {
        return memberCouponRepository.existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId());
    }

    private int getMaxQuantity(CouponEventRequest request) {
        Long couponId = request.getCouponId();
        if (maxQuantityMap.containsKey(couponId))
            return maxQuantityMap.get(couponId);

        int maxQuantity = couponEventRepository.findById(request.getCouponId())
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_RESULT))
                .getMaxQuantity();

        maxQuantityMap.put(couponId, maxQuantity);
        return maxQuantity;
    }

    private int getIssuedCouponCount(Long couponEventId){
        if (issuedCouponCount.containsKey(couponEventId))
            return issuedCouponCount.get(couponEventId);

        int issuedCouponCount = memberCouponRepository.countMemberCouponByCouponEventId(couponEventId);

        maxQuantityMap.put(couponEventId, issuedCouponCount);
        return issuedCouponCount;
    }
}
