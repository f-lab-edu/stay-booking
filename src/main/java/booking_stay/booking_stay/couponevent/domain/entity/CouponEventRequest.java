package booking_stay.booking_stay.couponevent.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponEventRequest {
    @NotBlank(message = "쿠폰이벤트아이디 필수값")
    private Long couponEventId;
    @NotBlank(message = "회원아이디 필수값")
    private String userId;
    @NotBlank(message = "지급쿠폰아이디 필수값")
    private Long couponId;

    @Builder
    public CouponEventRequest(String userId, Long couponId, Long couponEventId) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponEventId = couponEventId;
    }

    @Override
    public String toString() {
        return "CouponEventRequest{" +
                "couponEventId=" + couponEventId +
                ", userId='" + userId + '\'' +
                ", couponId=" + couponId +
                '}';
    }
}