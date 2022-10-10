package com.woowacourse.thankoo.coupon.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.coupon.application.CouponQueryService;
import com.woowacourse.thankoo.coupon.application.CouponService;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponSelectCommand;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponTotalResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CouponController {

    private final CouponService couponService;
    private final CouponQueryService couponQueryService;

    @Deprecated
    @PostMapping("/coupons/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @RequestBody final CouponRequest couponRequest) {
        couponService.saveAll(senderId, couponRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("organizations/{organizationId}/coupons/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @PathVariable final Long organizationId,
                                     @RequestBody final CouponRequest couponRequest) {
        couponService.saveAll(couponRequest.toCouponCommand(organizationId, senderId));
        return ResponseEntity.ok().build();
    }

    @Deprecated(since = "when organization will be merged")
    @GetMapping("/coupons/received")
    public ResponseEntity<List<CouponResponse>> receivedCoupons(@AuthenticationPrincipal final Long receiverId,
                                                                @RequestParam final String status) {
        return ResponseEntity.ok(couponQueryService.getReceivedCoupons(receiverId, status));
    }

    @GetMapping("/organizations/{organizationId}/coupons/received")
    public ResponseEntity<List<CouponResponse>> receivedCoupons(@AuthenticationPrincipal final Long receiverId,
                                                                @PathVariable final Long organizationId,
                                                                @RequestParam final String status) {
        CouponSelectCommand couponSelectCommand = new CouponSelectCommand(organizationId, receiverId, status);
        return ResponseEntity.ok(couponQueryService.getReceivedCouponsByOrganization(couponSelectCommand));
    }

    @GetMapping("/organizations/{organizationId}/coupons/sent")
    public ResponseEntity<List<CouponResponse>> sentCoupons(@AuthenticationPrincipal final Long senderId,
                                                            @PathVariable final Long organizationId) {
        return ResponseEntity.ok(couponQueryService.getSentCouponsByOrganization(organizationId, senderId));
    }

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponDetailResponse> getCoupon(@AuthenticationPrincipal final Long memberId,
                                                          @PathVariable final Long couponId) {
        return ResponseEntity.ok(couponQueryService.getCouponDetail(memberId, couponId));
    }

    @GetMapping("/coupons/count")
    public ResponseEntity<CouponTotalResponse> getCouponTotalCount(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(couponQueryService.getCouponTotalCount(memberId));
    }

    @PutMapping("/coupons/{couponId}/use")
    public ResponseEntity<Void> useCouponImmediately(@AuthenticationPrincipal final Long memberId,
                                                     @PathVariable final Long couponId) {
        couponService.useImmediately(memberId, couponId);
        return ResponseEntity.ok().build();
    }
}
