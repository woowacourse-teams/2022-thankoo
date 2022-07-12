package com.woowacourse.thankoo.coupon.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.coupon.application.CouponService;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @RequestBody final CouponRequest couponRequest) {
        couponService.saveAll(senderId, couponRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/received")
    public ResponseEntity<List<CouponResponse>> receivedCoupons(@AuthenticationPrincipal final Long receivedId) {
        return ResponseEntity.ok(couponService.getReceivedCoupons(receivedId));
    }
}
