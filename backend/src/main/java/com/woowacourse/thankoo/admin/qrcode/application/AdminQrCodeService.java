package com.woowacourse.thankoo.admin.qrcode.application;

import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminLinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminSerialRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AdminQrCodeService {

    private static final String URL = "http://api.qrserver.com/v1/create-qr-code/?data=https://thankoo.co.kr?code={0}&size=300x300";

    public List<AdminLinkResponse> getLinks(final AdminSerialRequest adminSerialRequest) {
        return adminSerialRequest.getSerials().stream()
                .map(serial -> new AdminLinkResponse(MessageFormat.format(URL, serial)))
                .collect(Collectors.toList());
    }
}
