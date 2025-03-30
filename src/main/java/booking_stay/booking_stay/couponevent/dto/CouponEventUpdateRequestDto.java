package booking_stay.booking_stay.couponevent.dto;

import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponEventUpdateRequestDto {
    private String eventName;
    private int maxQuantity;
    private CouponEventStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public CouponEventUpdateRequestDto(String eventName, int maxQuantity, CouponEventStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        this.eventName = eventName;
        this.maxQuantity = maxQuantity;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
