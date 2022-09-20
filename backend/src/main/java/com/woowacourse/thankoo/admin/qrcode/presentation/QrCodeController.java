package com.woowacourse.thankoo.admin.qrcode.presentation;

import com.woowacourse.thankoo.admin.qrcode.application.AdminQrCodeService;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminLinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminSerialRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/qrcode")
public class QrCodeController {

    private final AdminQrCodeService adminQrCodeService;

    @GetMapping
    @RequestMapping
    public ResponseEntity<List<AdminLinkResponse>> getQrCode(
            @RequestParam("adminSerialRequest") final List<AdminSerialRequest> adminSerialRequest) {
        return ResponseEntity.ok(adminQrCodeService.getLinks(adminSerialRequest));
    }
}
