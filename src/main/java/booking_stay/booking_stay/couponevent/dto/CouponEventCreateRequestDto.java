package booking_stay.booking_stay.couponevent.dto;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class CouponEventCreateRequestDto {
    private String eventName;
    private Long issuedCouponId;
    private int maxQuantity;
    private CouponEventStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public CouponEventCreateRequestDto(String eventName, Long issuedCouponId, int maxQuantity, CouponEventStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        this.eventName = eventName;
        this.issuedCouponId = issuedCouponId;
        this.maxQuantity = maxQuantity;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Dto <-> Entity 변환 어떤 방식이 좋은지?
     **/
    public CouponEvent toEntity(){
        return CouponEvent.builder()
                .eventName(eventName)
                .issuedCouponId(issuedCouponId)
                .maxQuantity(maxQuantity)
                .status(status)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
