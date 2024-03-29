package com.woowacourse.thankoo.admin.qrcode.presentation;

import com.woowacourse.thankoo.admin.qrcode.application.AdminQrCodeService;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminLinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminSerialRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminQrCodeController {

    private final AdminQrCodeService adminQrCodeService;

    @GetMapping("/qrcode")
    public ResponseEntity<List<AdminLinkResponse>> getQrCode(@RequestBody final AdminSerialRequest adminSerialRequest) {
        return ResponseEntity.ok(adminQrCodeService.getLinks(adminSerialRequest));
    }
}
