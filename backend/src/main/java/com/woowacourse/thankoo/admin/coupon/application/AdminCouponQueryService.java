package com.woowacourse.thankoo.admin.coupon.application;

import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponSearchRequest;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCoupon;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponQueryRepository;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponStatus;
import com.woowacourse.thankoo.admin.coupon.domain.dto.AdminCouponSearchCondition;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCouponQueryService {

    private final AdminCouponQueryRepository adminCouponQueryRepository;

    public List<AdminCouponResponse> getCoupons(final AdminCouponSearchRequest couponSearchRequest) {
        AdminCouponStatus couponStatus = AdminCouponStatus.of(couponSearchRequest.getStatus());
        AdminCouponSearchCondition couponSearchCondition = AdminCouponSearchCondition.of(
                couponStatus, couponSearchRequest.getStartDate(), couponSearchRequest.getEndDate());
        return getCouponsBySearchCondition(couponSearchCondition);
    }

    private List<AdminCouponResponse> getCouponsBySearchCondition(
            final AdminCouponSearchCondition couponSearchCondition) {
        List<AdminCoupon> coupons = adminCouponQueryRepository.findAllByStatusAndDate(couponSearchCondition);
        return ofCouponResponse(coupons);
    }

    private List<AdminCouponResponse> ofCouponResponse(final List<AdminCoupon> coupons) {
        return coupons.stream()
                .map(AdminCouponResponse::of)
                .collect(Collectors.toList());
    }
}
