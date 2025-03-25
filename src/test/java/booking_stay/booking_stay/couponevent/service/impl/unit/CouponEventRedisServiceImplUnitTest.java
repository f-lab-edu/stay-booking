package booking_stay.booking_stay.couponevent.service.impl.unit;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRedisRepository;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.service.impl.CouponEventRedisServiceImpl;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponEventRedisServiceImplUnitTest {

    @InjectMocks
    private CouponEventRedisServiceImpl couponEventRedisService;

    @Mock
    private CouponEventRedisRepository couponEventRedisRepository;

    @Mock
    private CouponEventRepository couponEventRepository;

    @Mock
    private MemberCouponRepository memberCouponRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void couponEventProducer() {
        CouponEventRequest request = CouponEventRequest.builder()
                .userId("테스트")
                .couponEventId(1L)
                .couponId(1L)
                .build();
        when(couponEventRedisRepository.getCouponEventMaxQuantity(request.getCouponEventId())).thenReturn(10);
        when(memberCouponRepository.existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId())).thenReturn(false);
        when(couponEventRedisRepository.addQueue(request)).thenReturn(true);

        // When
        String result = couponEventRedisService.couponEventProducer(request);

        // Then
        assertEquals("addQueue 성공", result);
        verify(couponEventRedisRepository, times(1)).getCouponEventMaxQuantity(request.getCouponEventId());
        verify(memberCouponRepository, times(1)).existsByUserIdAndCouponEventId(request.getUserId(), request.getCouponEventId());
        verify(couponEventRedisRepository, times(1)).addQueue(request);
    }

    @Test
    void couponEventConsumer() {
    }

    @Test
    void trunOnCouponEvent() {
    }

    @Test
    void resetCouponEventCount() {
    }
}