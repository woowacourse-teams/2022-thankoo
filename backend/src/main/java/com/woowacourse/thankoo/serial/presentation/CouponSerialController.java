package com.woowacourse.thankoo.serial.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.coupon.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.application.CouponSerialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon-serials")
public class CouponSerialController {

    private final CouponSerialService couponSerialService;

    @PostMapping
    public ResponseEntity<Void> createCouponWithSerialCode(@AuthenticationPrincipal final Long memberId,
                                                           @RequestBody final CouponSerialRequest couponSerialRequest) {
        couponSerialService.use(memberId, couponSerialRequest);
        return ResponseEntity.ok().build();
    }
}
