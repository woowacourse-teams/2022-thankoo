package com.woowacourse.thankoo.admin.common.qrcode.presentation;

import com.woowacourse.thankoo.admin.common.qrcode.infrastructure.QrCodeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/qrcode")
public class QrCodeController {

    private final QrCodeClient qrCodeClient;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getQrCode(@RequestParam("serial") final String serial) {
        return ResponseEntity.ok(qrCodeClient.getQrCode(serial));
    }
}
