package booking_stay.booking_stay.coupon.dto;

import booking_stay.booking_stay.coupon.domain.entity.Coupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponResponseDto {
    public String couponName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static CouponResponseDto toResponseDto(Coupon coupon) {
        return CouponResponseDto.builder()
                .couponName(coupon.getCouponName())
                .startTime(coupon.getStartTime())
                .endTime(coupon.getEndTime())
                .build();
    }
}
