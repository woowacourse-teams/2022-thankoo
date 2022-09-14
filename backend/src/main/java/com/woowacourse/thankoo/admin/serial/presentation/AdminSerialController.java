package com.woowacourse.thankoo.admin.serial.presentation;

import com.woowacourse.thankoo.admin.serial.application.AdminSerialService;
import com.woowacourse.thankoo.admin.serial.application.dto.SerialRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/serial")
public class AdminSerialController {

    private final AdminSerialService adminSerialService;

    @PostMapping
    public ResponseEntity<Void> createSerial(@RequestBody final SerialRequest serialRequest) {
        adminSerialService.save(serialRequest);
        return ResponseEntity.ok().build();
    }
}
