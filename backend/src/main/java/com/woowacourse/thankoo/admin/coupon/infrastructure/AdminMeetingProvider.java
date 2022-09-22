package com.woowacourse.thankoo.admin.coupon.infrastructure;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import java.util.List;

public interface AdminMeetingProvider {

    void finishMeetings(List<Coupon> coupons);
}
