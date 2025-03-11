package booking_stay.booking_stay.couponeventV2.dto;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponeventV2.domain.enums.CouponEventStatus;

import java.time.LocalDateTime;

public class CouponEventCreateRequestDto {
    private String eventName;
    private Long issuedCouponId;
    private int maxQuantity;
    private CouponEventStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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
