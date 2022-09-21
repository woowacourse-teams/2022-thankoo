package com.woowacourse.thankoo.serial.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.serial.application.CouponSerialQueryService;
import com.woowacourse.thankoo.serial.application.CouponSerialService;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon-serials")
public class CouponSerialController {

    private final CouponSerialQueryService couponSerialQueryService;
    private final CouponSerialService couponSerialService;

    @GetMapping
    public ResponseEntity<CouponSerialResponse> getCouponSerialBySerialCode(
            @AuthenticationPrincipal final Long memberId,
            @RequestParam final String code) {
        return ResponseEntity.ok(couponSerialQueryService.getCouponSerialByCode(memberId, code));
    }

    @PostMapping
    public ResponseEntity<Void> createCouponWithSerialCode(@AuthenticationPrincipal final Long memberId,
                                                           @RequestBody final CouponSerialRequest couponSerialRequest) {
        couponSerialService.use(memberId, couponSerialRequest);
        return ResponseEntity.ok().build();
    }
}
