package booking_stay.booking_stay.couponevent.service.impl.unit;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.couponevent.dto.CouponEventUpdateRequestDto;
import booking_stay.booking_stay.couponevent.service.impl.CouponEventServiceImpl;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // MockitoExtension을 사용하여 Mock 객체 초기화
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CouponEventServiceImplUnitTest {

    private static final Logger log = LoggerFactory.getLogger(CouponEventServiceImplUnitTest.class);
    @InjectMocks
    private CouponEventServiceImpl couponEventService; // 테스트 대상 클래스

    @Mock
    private CouponEventRepository couponEventRepository; // Mock 객체

    @Test
    void 쿠폰이벤트_생성_성공() {
        // Given
        CouponEventCreateRequestDto requestDto = mock(CouponEventCreateRequestDto.class);
        CouponEvent couponEvent = mock(CouponEvent.class);

        when(requestDto.toEntity()).thenReturn(couponEvent);
        when(couponEventRepository.save(couponEvent)).thenReturn(couponEvent);

        // When
        CouponEvent result = couponEventService.createCouponEvent(requestDto);

        // Then
        assertThat(result).isNotNull();
        verify(couponEventRepository, times(1)).save(couponEvent);
    }

    @Test
    void 쿠폰이벤트_업데이트_성공() {
        // Given
        Long couponEventId = 1L;
        CouponEventUpdateRequestDto requestDto = mock(CouponEventUpdateRequestDto.class);
        CouponEvent couponEvent = mock(CouponEvent.class);

        when(couponEventRepository.findById(couponEventId)).thenReturn(Optional.of(couponEvent));

        // When
        Long result = couponEventService.updateCouponEvent(couponEventId, requestDto);

        // Then
        assertThat(result).isEqualTo(couponEventId);
        verify(couponEvent, times(1)).update(any(), eq(requestDto));
    }

    @Test
    void 쿠폰이벤트_업데이트_실패_1() {
        Long couponEventId = 1L;
        CouponEventUpdateRequestDto requestDto = mock(CouponEventUpdateRequestDto.class);

        when(couponEventRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        BookingException ex = assertThrows(BookingException.class, () -> couponEventService.updateCouponEvent(couponEventId, requestDto));
//          assertEquals(ex.getMessage(), ErrorCode.NOT_EXIST_RESULT.name());
//        assertTh(() -> couponEventService.updateCouponEvent(couponEventId,requestDto))
//                ass
//                .isInstanceOf(BookingException.class)
////                .hasMessageContaining(ErrorCode.INPROGRESS_ERROR.name())
//                .extracting("httpStatus")
//                .isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    void 쿠폰이벤트_업데이트_실패() {
        Long couponEventId = 1L;
        CouponEvent couponEvent = CouponEvent.builder()
                .status(CouponEventStatus.DO)
                .build();
        CouponEventUpdateRequestDto requestDto = mock(CouponEventUpdateRequestDto.class);

        when(couponEventRepository.findById(anyLong())).thenReturn(Optional.of(couponEvent));
        
        
        // When & Then
        assertThrows(BookingException.class, () -> couponEventService.updateCouponEvent(couponEventId,requestDto));

//        assertThatThrownBy(() -> couponEventService.updateCouponEvent(couponEvent.getId(),requestDto))
//                .isInstanceOf(BookingException.class)
//                .hasMessageContaining(ErrorCode.INPROGRESS_ERROR.name())
//                .extracting("httpStatus")
//                .isEqualTo(HttpStatus.BAD_REQUEST);
//
//        verify(couponEvent, times(1)).update(eq(CouponEventStatus.DO), eq(requestDto));
    }

    @Test
    void 쿠폰상태값_종료로_변경() {
        // Given
        Long id = 1L;
        CouponEvent couponEvent = mock(CouponEvent.class);

        when(couponEventRepository.findCouponEventById(id)).thenReturn(Optional.of(couponEvent));

        // When
        CouponEvent result = couponEventService.updateCouponEventStatusFinish(id);

        // Then
        assertThat(result).isNotNull();
        verify(couponEvent, times(1)).changeStatusFinish();
    }

    @Test
    void 쿠폰이벤트_조회_성공() {
        // Given
        Long couponId = 1L;
        CouponEvent couponEvent = mock(CouponEvent.class);

        when(couponEventRepository.findById(couponId)).thenReturn(Optional.of(couponEvent));

        // When
        CouponEvent result = couponEventService.getCouponEvent(couponId);

        // Then
        assertThat(result).isNotNull();
    }


}
