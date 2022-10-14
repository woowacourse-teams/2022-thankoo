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

    @PostMapping("/coupons/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @RequestBody final CouponRequest couponRequest) {
        couponService.saveAll(couponRequest.toCouponCommand(senderId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/coupons/received")
    public ResponseEntity<List<CouponResponse>> receivedCoupons(@AuthenticationPrincipal final Long receiverId,
                                                                @RequestParam final Long organization,
                                                                @RequestParam final String status) {
        CouponSelectCommand couponSelectCommand = new CouponSelectCommand(organization, receiverId, status);
        return ResponseEntity.ok(couponQueryService.getReceivedCouponsByOrganization(couponSelectCommand));
    }

    @GetMapping("/organizations/{organizationId}/coupons/sent")
    public ResponseEntity<List<CouponResponse>> sentCoupons(@AuthenticationPrincipal final Long senderId,
                                                            @PathVariable final Long organizationId) {
        return ResponseEntity.ok(couponQueryService.getSentCouponsByOrganization(organizationId, senderId));
    }

    @GetMapping("/organizations/{organizationId}/coupons/{couponId}")
    public ResponseEntity<CouponDetailResponse> getCoupon(@AuthenticationPrincipal final Long memberId,
                                                          @PathVariable final Long organizationId,
                                                          @PathVariable final Long couponId) {
        return ResponseEntity.ok(couponQueryService.getCouponDetail(memberId, organizationId, couponId));
    }

    @GetMapping("/coupons/count")
    public ResponseEntity<CouponTotalResponse> getCouponTotalCount(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(couponQueryService.getCouponTotalCount(memberId));
    }

    @PutMapping("/organizations/{organizationId}/coupons/{couponId}/use")
    public ResponseEntity<Void> useCouponImmediately(@AuthenticationPrincipal final Long memberId,
                                                     @PathVariable final Long organizationId,
                                                     @PathVariable final Long couponId) {
        couponService.useImmediately(memberId, organizationId, couponId);
        return ResponseEntity.ok().build();
    }

}
