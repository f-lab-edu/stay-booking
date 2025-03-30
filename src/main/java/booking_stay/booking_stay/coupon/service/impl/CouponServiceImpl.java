package booking_stay.booking_stay.coupon.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.coupon.domain.entity.Coupon;
import booking_stay.booking_stay.coupon.domain.repository.CouponRepository;
import booking_stay.booking_stay.coupon.dto.CouponCreateRequestDto;
import booking_stay.booking_stay.coupon.dto.CouponResponseDto;
import booking_stay.booking_stay.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(CouponCreateRequestDto requestDto) {
        return null;
    }

    @Override
    public CouponResponseDto findByCouponId(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER));

        return CouponResponseDto.toResponseDto(coupon);
    }

    @Override
    public void resetCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER));

    }
}
