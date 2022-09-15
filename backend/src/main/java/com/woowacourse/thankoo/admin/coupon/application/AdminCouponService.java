package com.woowacourse.thankoo.admin.coupon.application;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponExpireRequest;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponException;
import com.woowacourse.thankoo.admin.coupon.infrastructure.AdminMeetingProvider;
import com.woowacourse.thankoo.admin.coupon.infrastructure.AdminReservationProvider;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCouponService {

    private final AdminCouponRepository adminCouponRepository;
    private final AdminMeetingProvider adminMeetingProvider;
    private final AdminReservationProvider adminReservationProvider;

    public void updateCouponStatusExpired(final AdminCouponExpireRequest couponExpireRequest) {
        Coupons coupons = new Coupons(getCoupons(couponExpireRequest.getCouponIds()));
        adminMeetingProvider.finishMeetings(coupons.getValues());
        adminReservationProvider.cancelReservation(coupons.getValues());
        adminCouponRepository.updateCouponStatus(CouponStatus.EXPIRED, coupons.getCouponIds());
    }

    private List<Coupon> getCoupons(final List<Long> couponIds) {
        List<Coupon> coupon = adminCouponRepository.findAllById(couponIds);
        validateCoupons(couponIds, coupon);

        return coupon;
    }

    private void validateCoupons(final List<Long> couponIds, final List<Coupon> coupons) {
        if (couponIds.size() != coupons.size()) {
            throw new AdminInvalidCouponException(AdminErrorType.NOT_FOUND_COUPON);
        }
    }
}
