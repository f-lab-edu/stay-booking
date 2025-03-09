package booking_stay.booking_stay.usercontents.service.impl;

import booking_stay.booking_stay.coupon.domain.entity.Coupon;
import booking_stay.booking_stay.usercontents.service.MemberCouponService;
import org.springframework.scheduling.annotation.Scheduled;

public class MemberCouponServiceImpl implements MemberCouponService {
    @Override
    public Object produceMemberCoupon(String user_id, Coupon coupon) {

//        redis or LinkedList에 인서트


        return null;
    }



    @Override
    @Scheduled()
    public Object insertMemberCoupon(String user_id, Coupon coupon) {

        // messageQue

        return null;
    }
}
