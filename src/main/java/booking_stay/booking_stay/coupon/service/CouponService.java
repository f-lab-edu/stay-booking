package booking_stay.booking_stay.coupon.service;

import booking_stay.booking_stay.coupon.domain.entity.Coupon;
import booking_stay.booking_stay.coupon.dto.CouponCreateRequestDto;
import booking_stay.booking_stay.coupon.dto.CouponResponseDto;

import java.util.Optional;

public interface CouponService {
    Coupon createCoupon(CouponCreateRequestDto requestDto);

    CouponResponseDto findByCouponId(Long couponId);

    void resetCoupon(Long couponId);


}
