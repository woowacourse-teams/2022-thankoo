package com.woowacourse.thankoo.admin.coupon.application;

import com.woowacourse.thankoo.admin.common.search.domain.AdminDateSearchCondition;
import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponSearchRequest;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCoupon;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponQueryRepository;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponStatus;
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
        AdminDateSearchCondition dateSearchCondition = AdminDateSearchCondition.of(
                couponSearchRequest.getStartDate(),
                couponSearchRequest.getEndDate()
        );

        if (couponStatus.isAll()) {
            return getCouponsByDateCondition(dateSearchCondition);
        }
        return getCouponsByStatusAndDateCondition(couponStatus, dateSearchCondition);
    }

    private List<AdminCouponResponse> getCouponsByDateCondition(final AdminDateSearchCondition dateSearchCondition) {
        List<AdminCoupon> coupons = adminCouponQueryRepository.findAllByDateCondition(
                dateSearchCondition.getStartDateTimeStringValue(),
                dateSearchCondition.getEndDateTimeStringValue());

        return ofCouponResponse(coupons);
    }

    private List<AdminCouponResponse> getCouponsByStatusAndDateCondition(final AdminCouponStatus couponStatus,
                                                                         final AdminDateSearchCondition dateSearchCondition) {
        List<AdminCoupon> coupons = adminCouponQueryRepository.findAllByStatusAndDateCondition(couponStatus.name(),
                dateSearchCondition.getStartDateTimeStringValue(),
                dateSearchCondition.getEndDateTimeStringValue()
        );

        return ofCouponResponse(coupons);
    }

    private List<AdminCouponResponse> ofCouponResponse(final List<AdminCoupon> coupons) {
        return coupons.stream()
                .map(AdminCouponResponse::of)
                .collect(Collectors.toList());
    }
}
