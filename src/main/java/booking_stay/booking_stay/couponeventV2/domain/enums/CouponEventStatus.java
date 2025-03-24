package booking_stay.booking_stay.couponeventV2.domain.enums;

import lombok.Getter;

@Getter
public enum CouponEventStatus {
    READY("RD"),
    DO("DO"),
    FINISH("FN");

    private final String code;

    CouponEventStatus(String code) {
        this.code = code;
    }
}