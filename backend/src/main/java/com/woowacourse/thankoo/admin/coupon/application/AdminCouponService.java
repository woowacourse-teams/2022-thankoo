package com.woowacourse.thankoo.admin.coupon.application;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponQueryRepository;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCouponService {

    private final AdminCouponQueryRepository adminCouponQueryRepository;

    public List<AdminCouponResponse> getCoupons() {
        List<MemberCoupon> coupons = adminCouponQueryRepository.findAll();
        return coupons.stream()
                .map(AdminCouponResponse::of)
                .collect(Collectors.toList());
    }
}
