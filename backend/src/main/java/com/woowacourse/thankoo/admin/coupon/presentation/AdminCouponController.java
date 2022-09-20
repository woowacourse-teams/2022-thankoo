package com.woowacourse.thankoo.admin.coupon.presentation;

import com.woowacourse.thankoo.admin.coupon.application.AdminCouponQueryService;
import com.woowacourse.thankoo.admin.coupon.application.AdminCouponService;
import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponExpireRequest;
import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponSearchRequest;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;
    private final AdminCouponQueryService adminCouponQueryService;

    @GetMapping
    public ResponseEntity<List<AdminCouponResponse>> getCoupons(
            @ModelAttribute final AdminCouponSearchRequest couponSearchRequest) {
        return ResponseEntity.ok(adminCouponQueryService.getCoupons(couponSearchRequest));
    }

    @PutMapping("/expire")
    public ResponseEntity<Void> updateCouponStatusExpired(
            @RequestBody final AdminCouponExpireRequest couponExpireRequest) {
        adminCouponService.updateCouponStatusExpired(couponExpireRequest);
        return ResponseEntity.noContent().build();
    }
}
