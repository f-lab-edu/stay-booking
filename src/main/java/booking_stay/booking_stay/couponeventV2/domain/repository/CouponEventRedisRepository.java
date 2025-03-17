package booking_stay.booking_stay.couponeventV2.domain.repository;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponEventRedisRepository {
    private final RedisTemplate redisTemplate;
    private static final String COUPON_EVENT_PREFIX = "coupon_event_queue";
    private static final String COUPON_EVENT_MAX_QUANTITY = "coupon_max_quantity";

    public Long getCount() {
        return redisTemplate.opsForZSet().zCard(COUPON_EVENT_PREFIX);
    }

    public void delete() {
        redisTemplate.delete(COUPON_EVENT_PREFIX);
    }

    public String popMinIfExists() {
        if (getCount() < 1) return null;
        var tuple = redisTemplate.opsForZSet().popMin(COUPON_EVENT_PREFIX);
        return (tuple==null) ? null : (String) tuple.getValue();
    }

    public Boolean addIfAbsent(String key , String value) {
        return redisTemplate.opsForZSet().addIfAbsent(
                key, value, System.currentTimeMillis()
        );
    }

    public Boolean setCouponMaxQuantity(Long couponEventId , int quantity) {
        return redisTemplate.opsForValue().setIfAbsent(COUPON_EVENT_MAX_QUANTITY + ":" + couponEventId, quantity);
    }
    public Integer getCouponEventMaxQuantity(Long couponEventId) {
        return (Integer) redisTemplate.opsForValue().get(COUPON_EVENT_MAX_QUANTITY + ":" +couponEventId);
    }

    public Boolean existsByKeyAndUserId(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value) != null;
    }

}
