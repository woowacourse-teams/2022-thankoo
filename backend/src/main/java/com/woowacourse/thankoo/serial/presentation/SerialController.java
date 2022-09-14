package com.woowacourse.thankoo.serial.presentation;

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
@RequestMapping("/admin/serial")
public class SerialController {

    private final CouponSerialService couponSerialService;
    private final CouponSerialQueryService couponSerialQueryService;

    @GetMapping
    public ResponseEntity<CouponSerialResponse> getSerial(@RequestParam("code") final String code) {
        return ResponseEntity.ok(couponSerialQueryService.getByCode(code));
    }

    @PostMapping
    public ResponseEntity<Void> createSerial(@RequestBody final CouponSerialRequest couponSerialRequest) {
        couponSerialService.save(couponSerialRequest);
        return ResponseEntity.ok().build();
    }
}
