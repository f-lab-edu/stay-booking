package booking_stay.booking_stay.coupon.domain.entity;

import booking_stay.booking_stay.coupon.domain.enums.CouponPeriodType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "COUPON")
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private String minimunOrderAmount;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedTime;

    @Column(nullable = false)
    private CouponPeriodType couponPeriodType;

    @Column(nullable = false)
    private int couponPeriod;

    @Column
    private LocalDateTime couponExpirationDate;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    //    기간유형에 따라 couponPeroid 랑 couponExpirationDate에 값 넣어주고싶은데 방법생각안남
    @Builder
    public Coupon(String couponName, String minimunOrderAmount, CouponPeriodType couponPeriodType, int couponPeriod, LocalDateTime couponExpirationDate, LocalDateTime startTime, LocalDateTime endTime) {
        this.couponName = couponName;
        this.minimunOrderAmount = minimunOrderAmount;
        this.couponPeriodType = couponPeriodType;
        this.couponPeriod = couponPeriod;
        this.couponExpirationDate = couponExpirationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}