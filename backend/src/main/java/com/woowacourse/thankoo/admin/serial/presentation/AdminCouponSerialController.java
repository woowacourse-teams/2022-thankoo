package com.woowacourse.thankoo.admin.serial.presentation;

import com.woowacourse.thankoo.admin.serial.application.AdminCouponSerialService;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/serial")
public class AdminCouponSerialController {

    private final AdminCouponSerialService adminCouponSerialService;

    @PostMapping
    public ResponseEntity<Void> createSerial(@RequestBody final CouponSerialRequest couponSerialRequest) {
        adminCouponSerialService.save(couponSerialRequest);
        return ResponseEntity.ok().build();
    }
}
