package booking_stay.booking_stay.couponeventV2.domain.entity;

import lombok.Getter;

@Getter
public class CouponEventRequest {
    private String userId;
    private Long couponId;
    private Long couponEventId;
}
