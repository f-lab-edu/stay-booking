package booking_stay.booking_stay.couponeventV2.service.impl;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponeventV2.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventUpdateRequestDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponEventServiceImplTest {

    @Autowired
    private CouponEventServiceImpl couponEventService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponEventRepository couponEventRepository;

    @Autowired
    private EntityManager entityManager;

    private CouponEvent defaultCouponEvent;

    @BeforeEach
    void setUp() {
        CouponEventCreateRequestDto requestDto =
                CouponEventCreateRequestDto.builder()
                        .eventName("쿠폰지급이벤트1")
                        .issuedCouponId(1L)
                        .maxQuantity(1000)
                        .status(CouponEventStatus.READY)
                        .startTime(LocalDateTime.now().minusDays(2))
                        .endTime(LocalDateTime.now().plusDays(2))
                        .build();

        defaultCouponEvent = couponEventService.createCouponEvent(requestDto);
    }

    @Test
    @DisplayName("쿠폰이벤트 생성")
    public void createCouponEventTest() throws Exception{
        CouponEventCreateRequestDto requestDto =
                CouponEventCreateRequestDto.builder()
                        .eventName("쿠폰지급이벤트3")
                        .issuedCouponId(3L)
                        .maxQuantity(3000)
                        .status(CouponEventStatus.DO)
                        .startTime(LocalDateTime.now().minusDays(2))
                        .endTime(LocalDateTime.now().plusDays(2))
                        .build();

        couponEventService.createCouponEvent(requestDto);
    }

    @Test
    @DisplayName("쿠폰이벤트 업데이트")
    public void updateCouponEventTest() throws Exception{
        //given
        Long couponEventId = defaultCouponEvent.getId();
        CouponEventUpdateRequestDto updateRequestDto = CouponEventUpdateRequestDto.builder()
                .eventName(defaultCouponEvent.getEventName() + "변경")
                .maxQuantity(defaultCouponEvent.getMaxQuantity()+1000)
                .status(defaultCouponEvent.getStatus())
                .startTime(defaultCouponEvent.getStartTime())
                .endTime(defaultCouponEvent.getEndTime())
                .build();
        //when
        couponEventService.updateCouponEvent(couponEventId, updateRequestDto);

        //then
        CouponEvent updatedCouponEvent = couponEventRepository.findCouponEventById(couponEventId).orElseThrow();
        assertEquals(updateRequestDto.getEventName(), updatedCouponEvent.getEventName());
        assertEquals(updateRequestDto.getMaxQuantity(), updatedCouponEvent.getMaxQuantity());
    }

    @Test
    @DisplayName("쿠폰 이벤트 참여 테스트")
    public void couponPublisherTest() throws Exception{
        //given
        CouponEventRequest request = CouponEventRequest.builder()
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .userId("테스트아이디1")
                .build();
        //when
        String result = couponEventService.couponEventPublisher(request);

        //then
        assertEquals("참여 완료",result);
    }

    @Test
    @DisplayName("쿠폰 이벤트 중복 참여 테스트")
    @Transactional
    public void couponPublisherDuplicateTest() throws Exception{
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

        String result = couponEventService.couponEventPublisher(duplicatedRequest);

        //then
        assertEquals("중복참여 불가",result);
    }

}