package booking_stay.booking_stay.couponevent.service.impl;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.service.CouponEventIssueCommand;
import booking_stay.booking_stay.couponevent.service.CouponEventIssueQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponEventIssueServiceImpl implements CouponEventIssueCommand, CouponEventIssueQuery {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String COUPON_EVENT_QUEUE = "coupon_event_queue";
    private static final String COUPON_EVENT_MAX_QUANTITY = "coupon_max_quantity";

    @Override
    public Boolean addQueue(CouponEventRequest request) {
        return redisTemplate.opsForZSet().add(COUPON_EVENT_QUEUE,  request, System.currentTimeMillis());
    }

    @Override
    public void resetCouponMaxQuantity(Long couponEventId, Integer quantity) {
        redisTemplate.opsForValue().set(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId, quantity);
    }

    @Override
    public Boolean setCouponMaxQuantity(Long couponEventId, Integer quantity) {
        return redisTemplate.opsForValue().setIfAbsent(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId, quantity);
    }

    @Override
    public Long decreaseCouponMaxQuantity(Long couponEventId) {
        return redisTemplate.opsForValue().decrement(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId);
    }

    @Override
    public Long getExistCouponEventCount() {
        return redisTemplate.opsForZSet().zCard(COUPON_EVENT_QUEUE);
    }

    @Override
    public ZSetOperations.TypedTuple<Object> popMinOne() {
        return redisTemplate.opsForZSet().popMin(COUPON_EVENT_QUEUE);
    }

    @Override
    public Integer getCouponEventMaxQuantity(Long couponEventId) {
        return (Integer) redisTemplate.opsForValue().get(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId);
    }
}
