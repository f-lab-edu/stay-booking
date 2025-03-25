package booking_stay.booking_stay.couponevent.domain.repository;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CouponEventRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String COUPON_EVENT_QUEUE = "coupon_event_queue";
    private static final String COUPON_EVENT_MAX_QUANTITY = "coupon_max_quantity";

    public Long getExistCouponEventCount() {
        return redisTemplate.opsForZSet().zCard(COUPON_EVENT_QUEUE);
    }

    public Set<ZSetOperations.TypedTuple<Object>> popMin(Long count) {
        return redisTemplate.opsForZSet().popMin(COUPON_EVENT_QUEUE, count);
    }

    public ZSetOperations.TypedTuple<Object> popMinOne(){
        return redisTemplate.opsForZSet().popMin(COUPON_EVENT_QUEUE);
    }

    public Boolean addQueue(CouponEventRequest request) {
        return redisTemplate.opsForZSet().add(COUPON_EVENT_QUEUE,  request, System.currentTimeMillis());
    }

    public void resetCouponMaxQuantity(Long couponEventId , Integer quantity) {
        redisTemplate.opsForValue().set(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId, quantity);
    }

    public Boolean setCouponMaxQuantity(Long couponEventId , Integer quantity) {
        return redisTemplate.opsForValue().setIfAbsent(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId, quantity);
    }
    public Integer getCouponEventMaxQuantity(Long couponEventId) {
        return (Integer) redisTemplate.opsForValue().get(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId);
    }

    public Long decreaseCouponMaxQuantity(Long couponEventId) {
        return redisTemplate.opsForValue().decrement(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId);
    }

}