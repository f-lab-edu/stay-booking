package booking_stay.booking_stay.coupon.domain.enums;

import java.time.LocalDateTime;
import java.util.function.Function;

public enum CouponPeriodType {
    DAY("DAY", (Function<Integer, LocalDateTime>) days -> LocalDateTime.now().plusDays(days)),
    Testa("Testa", days -> LocalDateTime.now().plusDays((Long) days)),
    YEAR("YEAR",(Function<Integer, LocalDateTime>)years -> LocalDateTime.now().plusYears(years)),
    FIX("FIX", (Function<LocalDateTime, LocalDateTime>)date -> date)
    ;

    private String code;
    private Function<?, LocalDateTime> extractor;

    //와일드카드 제네릭 안쓰고 Object로 하면?

    <T> CouponPeriodType(String code, Function<T, LocalDateTime> extractor) {
        this.code = code;
        this.extractor = extractor;
    }

    @SuppressWarnings("unchecked")
    public <T>LocalDateTime extract(T input) {
        return ((Function<T, LocalDateTime>) extractor).apply(input);
    }

}