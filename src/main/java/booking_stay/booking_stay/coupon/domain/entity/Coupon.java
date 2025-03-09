package booking_stay.booking_stay.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "COUPON")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false)
    private String couponName;


//  쿠폰 발행 유효기간 (발행 가능 불가능)
//  쿠폰의 적용 기간
//  쿠폰 적용 타입 ( 만료일 지정, +N day, 적용시작/종료일)

    @Column(nullable = false)
    private LocalDateTime start_time;

    @Column(nullable = false)
    private LocalDateTime end_time;

}
