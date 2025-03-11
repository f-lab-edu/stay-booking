package booking_stay.booking_stay.couponeventV2.dto;

import booking_stay.booking_stay.couponeventV2.domain.enums.CouponEventStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponEventUpdateRequestDto {
    private String eventName;
    private int maxQuantity;
    private CouponEventStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
