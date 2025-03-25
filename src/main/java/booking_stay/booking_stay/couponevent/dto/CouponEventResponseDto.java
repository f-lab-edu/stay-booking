package booking_stay.booking_stay.couponevent.dto;

import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CouponEventResponseDto {
    private Long id;
    private String eventName;
    private Long issuedCouponId;
    private int maxQuantity;
    private CouponEventStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
