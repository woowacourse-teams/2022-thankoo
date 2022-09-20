package com.woowacourse.thankoo.admin.qrcode.application;

import com.woowacourse.thankoo.admin.qrcode.presentation.dto.LinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.SerialRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class QrCodeService {

    private static final String URL = "http://api.qrserver.com/v1/create-qr-code/?data=https://thankoo.co.kr/code={0}&size=300x300";

    public List<LinkResponse> getLinks(final List<SerialRequest> serialRequest) {
        return serialRequest.stream()
                .map(request -> new LinkResponse(MessageFormat.format(URL, request.getSerial())))
                .collect(Collectors.toList());
    }
}
