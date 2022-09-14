package com.woowacourse.thankoo.admin.common.qrcode.infrastructure;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QrCodeClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public Resource getQrCode(final String serial) {
        return REST_TEMPLATE.getForEntity(
                "http://api.qrserver.com/v1/create-qr-code/?data=https://thankoo.co.kr/code=" + serial + "&size=300x300",
                Resource.class)
                .getBody();
    }
}
