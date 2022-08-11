package com.woowacourse.thankoo.coupon.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.coupon.application.CouponQueryService;
import com.woowacourse.thankoo.coupon.application.CouponService;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponTotalResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;
    private final CouponQueryService couponQueryService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @RequestBody final CouponRequest couponRequest) {
        couponService.saveAll(senderId, couponRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/received")
    public ResponseEntity<List<CouponResponse>> receivedCoupons(@AuthenticationPrincipal final Long receiverId,
                                                                @RequestParam final String status) {
        return ResponseEntity.ok(couponQueryService.getReceivedCoupons(receiverId, status));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<CouponResponse>> sentCoupons(@AuthenticationPrincipal final Long senderId) {
        return ResponseEntity.ok(couponQueryService.getSentCoupons(senderId));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailResponse> getCoupon(@AuthenticationPrincipal final Long memberId,
                                                          @PathVariable final Long couponId) {
        return ResponseEntity.ok(couponQueryService.getCouponDetail(memberId, couponId));
    }

    @GetMapping("/count")
    public ResponseEntity<CouponTotalResponse> getCouponTotalCount(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(couponQueryService.getCouponTotalCount(memberId));
    }
}
