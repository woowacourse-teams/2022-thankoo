package com.woowacourse.thankoo.coupon.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.coupon.application.CouponHistoryService;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponHistoryResponse;
import java.net.URI;
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
public class CouponHistoryController {

    private final CouponHistoryService couponHistoryService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @RequestBody final CouponRequest couponRequest) {
        List<Long> ids = couponHistoryService.saveAll(senderId, couponRequest);
        return ResponseEntity.created(URI.create("/api/coupons/" + ids.get(0))).build();
    }

    @GetMapping("/received")
    public ResponseEntity<List<CouponHistoryResponse>> receivedCoupons(@AuthenticationPrincipal final Long receivedId) {
        return ResponseEntity.ok(couponHistoryService.getReceivedCoupons(receivedId));
    }
}
