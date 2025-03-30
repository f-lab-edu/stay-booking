package booking_stay.booking_stay.coupon.dto;

import booking_stay.booking_stay.coupon.domain.entity.Coupon;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponCreateRequestDto {
    public String couponName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(couponName)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

}
