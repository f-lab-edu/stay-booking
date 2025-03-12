package booking_stay.booking_stay.couponeventV2.domain.enums;

import lombok.Getter;

@Getter
public enum CouponEventStatus {
    Ready("RD"),
    Do("DO"),
    Finish("FN");

    private final String code;

    CouponEventStatus(String code) {
        this.code = code;
    }
}
