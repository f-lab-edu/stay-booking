package booking_stay.booking_stay.couponeventV2.service.impl;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponEventRedisServiceImplTest {
    @Autowired
    private CouponEventServiceImpl couponEventService;

    @Autowired
    private CouponEventRedisServiceImpl couponEventRedisService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private EntityManager entityManager;

    private CouponEvent defaultCouponEvent;

    @BeforeEach
    void setUp() {
        CouponEventCreateRequestDto requestDto =
                CouponEventCreateRequestDto.builder()
                        .eventName("쿠폰지급이벤트1")
                        .issuedCouponId(1L)
                        .maxQuantity(3000)
                        .status(CouponEventStatus.Do)
                        .startTime(LocalDateTime.now().minusDays(2))
                        .endTime(LocalDateTime.now().plusDays(2))
                        .build();

        defaultCouponEvent = couponEventService.createCouponEvent(requestDto);
    }

    @Test
    @DisplayName("쿠폰 이벤트 참여 테스트")
    public void couponProducerTest() throws Exception{
        //given
        CouponEventRequest request = CouponEventRequest.builder()
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .userId("테스트아이디1")
                .build();
        //when
        String result = couponEventRedisService.couponEventProducer(request);

        //then
        assertEquals("addQueue 성공",result);
    }


    @Test
    @DisplayName("쿠폰 이벤트 중복 참여 테스트")
    @Transactional
    public void couponProducerDuplicateTest() throws Exception{
        //given
        MemberCoupon alreadyExistMemberCoupon = MemberCoupon.builder()
                .userId("테스트아이디1")
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .build();

        memberCouponRepository.save(alreadyExistMemberCoupon);
        entityManager.flush();
        entityManager.clear();

        //when
        CouponEventRequest duplicatedRequest = CouponEventRequest.builder()
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .userId("테스트아이디1")
                .build();

        String result = couponEventRedisService.couponEventProducer(duplicatedRequest);

        //then
        assertEquals("중복참여 불가",result);
    }


    @Test
    @DisplayName("쿠폰 이벤트 컨슈머 테스트")
    public void couponConsumerTest() throws Exception{
        //given
        for (int i =0; i < 100; i++){
            CouponEventRequest request = CouponEventRequest.builder()
                    .couponId(defaultCouponEvent.getIssuedCouponId())
                    .couponEventId(defaultCouponEvent.getId())
                    .userId("테스트아이디"+i)
                    .build();

            couponEventRedisService.couponEventProducer(request);
        }

        Thread.sleep(100000);

    }


}