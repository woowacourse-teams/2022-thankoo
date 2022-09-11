package com.woowacourse.thankoo.admin.coupon.presentation;

import com.woowacourse.thankoo.admin.coupon.application.AdminCouponService;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    @GetMapping
    public ResponseEntity<List<AdminCouponResponse>> getCoupons() {
        return ResponseEntity.ok(adminCouponService.getCoupons());
    }
}
