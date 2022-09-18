package com.woowacourse.thankoo.admin.serial.presentation;

import com.woowacourse.thankoo.admin.serial.application.AdminCouponSerialQueryService;
import com.woowacourse.thankoo.admin.serial.application.AdminCouponSerialService;
import com.woowacourse.thankoo.admin.serial.presentation.dto.CouponSerialResponse;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import java.util.List;
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
public class AdminCouponSerialController {

    private final AdminCouponSerialService couponSerialService;
    private final AdminCouponSerialQueryService couponSerialQueryService;

    @GetMapping
    public ResponseEntity<List<CouponSerialResponse>> getSerial(@RequestParam("memberId") final Long memberId) {
        return ResponseEntity.ok(couponSerialQueryService.getByMemberId(memberId));
    }
    @PostMapping
    public ResponseEntity<Void> createSerial(@RequestBody final CouponSerialRequest couponSerialRequest) {
        couponSerialService.save(couponSerialRequest);
        return ResponseEntity.ok().build();
    }
}