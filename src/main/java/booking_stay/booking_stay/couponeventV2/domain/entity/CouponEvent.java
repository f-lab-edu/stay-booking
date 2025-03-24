package booking_stay.booking_stay.couponeventV2.domain.entity;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.couponeventV2.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventResponseDto;
import booking_stay.booking_stay.couponeventV2.dto.CouponEventUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "COUPON_EVENT")
@NoArgsConstructor
public class CouponEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_event_id")
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private Long issuedCouponId;

    @Column(nullable = false)
    private int maxQuantity;

    @Column(nullable = false)
    private CouponEventStatus status;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateTime;

    @Builder
    public CouponEvent(String eventName, Long issuedCouponId, int maxQuantity, CouponEventStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        this.eventName = eventName;
        this.issuedCouponId = issuedCouponId;
        this.maxQuantity = maxQuantity;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void changeStatus(CouponEventStatus status){
        this.status = status;
    }

    public void update(CouponEventStatus currentStatus, CouponEventUpdateRequestDto requestDto) {
        if (currentStatus.equals(CouponEventStatus.DO))
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.INPROGRESS_ERROR);

        this.eventName = requestDto.getEventName();
        this.maxQuantity = requestDto.getMaxQuantity();
        this.status = requestDto.getStatus();
        this.startTime = requestDto.getStartTime();
        this.endTime = requestDto.getEndTime();
    }

    public CouponEventResponseDto toResponseDto() {
        return CouponEventResponseDto.builder()
                .id(id)
                .issuedCouponId(issuedCouponId)
                .maxQuantity(maxQuantity)
                .status(status)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}