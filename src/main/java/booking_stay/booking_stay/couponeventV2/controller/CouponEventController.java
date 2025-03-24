package booking_stay.booking_stay.couponeventV2.controller;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponeventV2.service.CouponEventRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/coupon-event")
@RestController
public class CouponEventController {

    private final CouponEventRedisService couponEventRedisService;

    @PostMapping("/apply")
    public String applyCouponEvent(@RequestBody CouponEventRequest request) {
        return couponEventRedisService.couponEventProducer(request);
    }

    @PutMapping("/trun-on/{id}")
    public String trunOnCouponEvent(@PathVariable Long id) {
        return couponEventRedisService.trunOnCouponEvent(id);
    }

    @PutMapping("/reset/{id}")
    public String resetCouponEventCount(@PathVariable Long id) {
        return couponEventRedisService.trunOnCouponEvent(id);
    }


}