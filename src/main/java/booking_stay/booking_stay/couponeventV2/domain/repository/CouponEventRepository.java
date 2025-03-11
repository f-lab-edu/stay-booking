package booking_stay.booking_stay.couponeventV2.domain.repository;

import booking_stay.booking_stay.couponeventV2.domain.entity.CouponEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponEventRepository extends JpaRepository<CouponEvent,Long> {
    Optional<CouponEvent> findCouponEventById(Long couponEventId);
}
