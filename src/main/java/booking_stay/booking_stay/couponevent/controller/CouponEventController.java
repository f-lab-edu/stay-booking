package booking_stay.booking_stay.couponevent.controller;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.service.CouponEventFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/coupon-event")
@RestController
public class CouponEventController {

    private final CouponEventFacade couponEventFacade;

    @PostMapping("/apply")
    public String applyCouponEvent(@RequestBody CouponEventRequest request) {
        return couponEventFacade.couponEventProducer(request);
    }

    @PutMapping("/trun-on/{id}")
    public String trunOnCouponEvent(@PathVariable Long id) {
        return couponEventFacade.turnOnCouponEvent(id);
    }

    @PutMapping("/reset/{id}")
    public String resetCouponEventCount(@PathVariable Long id) {
        return couponEventFacade.resetCouponEventCount(id);
    }


}