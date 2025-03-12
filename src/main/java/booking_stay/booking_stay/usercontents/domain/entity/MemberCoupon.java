package booking_stay.booking_stay.usercontents.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "MEMBER_COUPON")
@NoArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long couponId;

//    long값의 경우 null이 나은지 default 0으로 넣는게 나은지
    @Column
    private Long couponEventId;

    @Builder(builderMethodName = "memberCouponSimpleBuilder")
    public MemberCoupon(String userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }

    @Builder(builderMethodName = "memberCouponWithEventIdBuilder")
    public MemberCoupon(String userId, Long couponId, Long couponEventId) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponEventId = couponEventId;
    }

    @Override
    public String toString() {
        return "MemberCoupon{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", couponId=" + couponId +
                '}';
    }
}
