package com.woowacourse.thankoo.serial.presentation;

import com.woowacourse.thankoo.admin.serial.presentation.dto.CouponSerialResponse;
import com.woowacourse.thankoo.serial.application.CouponSerialQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/serial")
public class CouponSerialController {

    private final CouponSerialQueryService couponSerialQueryService;

    @GetMapping
    public ResponseEntity<CouponSerialResponse> getSerial(@RequestParam("code") final String code) {
        return ResponseEntity.ok(couponSerialQueryService.getByCode(code));
    }
}
