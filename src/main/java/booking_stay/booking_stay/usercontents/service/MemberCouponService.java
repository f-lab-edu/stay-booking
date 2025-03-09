package booking_stay.booking_stay.usercontents.service;

import booking_stay.booking_stay.coupon.domain.entity.Coupon;

public interface MemberCouponService {
    Object produceMemberCoupon(String user_id, Coupon coupon);

    Object insertMemberCoupon(String user_id, Coupon coupon);
}
