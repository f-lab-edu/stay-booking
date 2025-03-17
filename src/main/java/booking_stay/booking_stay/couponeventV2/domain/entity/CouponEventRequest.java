package booking_stay.booking_stay.couponeventV2.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponEventRequest {
    private String userId;
    private Long couponId;
    private Long couponEventId;

    @Builder
    public CouponEventRequest(String userId, Long couponId, Long couponEventId) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponEventId = couponEventId;
    }

    public String getRedisKey(String key){
        return key + ":" + couponEventId;
    }
}
