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
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long couponId;

    @Builder
    public MemberCoupon(String userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
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
