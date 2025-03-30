package booking_stay.booking_stay.couponevent.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CouponEventServiceImplTest {

    @Autowired
    private CouponEventServiceImpl couponEventService;

    @Autowired
    private CouponEventIssueServiceImpl couponEventIssueService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponEventRepository couponEventRepository;

    @Autowired
    private EntityManager entityManager;

    private CouponEvent defaultCouponEvent;
    private CouponEvent checkExceptionCouponEvent;

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

        CouponEventCreateRequestDto requestDto2 =
                CouponEventCreateRequestDto.builder()
                        .eventName("쿠폰지급이벤트1")
                        .issuedCouponId(1L)
                        .maxQuantity(1000)
                        .status(CouponEventStatus.DO)
                        .startTime(LocalDateTime.now().minusDays(2))
                        .endTime(LocalDateTime.now().plusDays(2))
                        .build();

        checkExceptionCouponEvent = couponEventService.createCouponEvent(requestDto2);
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
    @DisplayName("쿠폰이벤트 업데이트 실패")
    public void updateCouponEventTestThrowException() throws Exception{
        //given
        Long couponEventId = checkExceptionCouponEvent.getId();
        CouponEventUpdateRequestDto updateRequestDto = CouponEventUpdateRequestDto.builder()
                .eventName(checkExceptionCouponEvent.getEventName() + "변경")
                .maxQuantity(checkExceptionCouponEvent.getMaxQuantity()+1000)
                .status(CouponEventStatus.FINISH)
                .startTime(checkExceptionCouponEvent.getStartTime())
                .endTime(checkExceptionCouponEvent.getEndTime())
                .build();
        //when
//        couponEventService.updateCouponEvent(couponEventId, updateRequestDto);

        //then
        assertThrows(BookingException.class, () -> couponEventService.updateCouponEvent(couponEventId, updateRequestDto));


    }

}