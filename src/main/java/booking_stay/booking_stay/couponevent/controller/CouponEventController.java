package booking_stay.booking_stay.couponevent.controller;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.service.CouponEventService;
import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CouponEventController {

    private final CouponEventService couponEventService;

    @PostMapping("/coupon-event")
    public String isuueCoupon(@RequestBody CouponEventRequest request) {
        return couponEventService.couponEventPublisher(request);
    }

}
