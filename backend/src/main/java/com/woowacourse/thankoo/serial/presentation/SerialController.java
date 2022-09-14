package com.woowacourse.thankoo.serial.presentation;

import com.woowacourse.thankoo.serial.application.SerialService;
import com.woowacourse.thankoo.serial.application.dto.SerialRequest;
import com.woowacourse.thankoo.serial.presentation.dto.SerialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/serial")
public class SerialController {

    private final SerialService serialService;

    @GetMapping
    public ResponseEntity<SerialResponse> getSerial() {
        return null;
    }

    @PostMapping
    public ResponseEntity<Void> createSerial(@RequestBody final SerialRequest serialRequest) {
        serialService.save(serialRequest);
        return ResponseEntity.ok().build();
    }
}
