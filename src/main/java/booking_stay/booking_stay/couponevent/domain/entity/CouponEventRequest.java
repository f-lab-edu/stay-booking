package booking_stay.booking_stay.couponevent.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponEventRequest {
    private String userId;
    private Long couponId;
}
