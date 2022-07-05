package com.woowacourse.thankoo.coupon.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.coupon.application.CouponHistoryService;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponHistoryController {

    private final CouponHistoryService couponHistoryService;

    @PostMapping("/api/coupons/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long senderId,
                                     @RequestBody final CouponRequest couponRequest) {
        Long id = couponHistoryService.save(senderId, couponRequest);
        return ResponseEntity.created(URI.create("/api/coupons/" + id)).build();
    }
}
