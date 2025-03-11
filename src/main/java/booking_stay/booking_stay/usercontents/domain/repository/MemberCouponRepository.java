package booking_stay.booking_stay.usercontents.domain.repository;

import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    Boolean existsByUserIdAndCouponEventId(String userId, Long couponEventId);

    int countMemberCouponByCouponEventId(Long couponEventId);
}
