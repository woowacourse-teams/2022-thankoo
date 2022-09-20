package com.woowacourse.thankoo.admin.common.qrcode.presentation;

import com.woowacourse.thankoo.admin.common.qrcode.application.QrCodeService;
import com.woowacourse.thankoo.admin.common.qrcode.presentation.dto.LinkResponse;
import com.woowacourse.thankoo.admin.common.qrcode.presentation.dto.SerialRequest;
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

    private final QrCodeService qrCodeService;

    @GetMapping
    @RequestMapping
    public ResponseEntity<List<LinkResponse>> getQrCode(@RequestParam("serial") final List<SerialRequest> serialRequest) {
        return ResponseEntity.ok(qrCodeService.getLinks(serialRequest));
    }
}
