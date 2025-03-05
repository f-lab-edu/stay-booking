package booking_stay.booking_stay.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    private String couponName;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

}
