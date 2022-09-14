package com.woowacourse.thankoo.serial.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SerialResponse {

    private Long id;
    private String code;
    private Long memberId;
    private Long memberName;
    private String couponType;

    public SerialResponse(final Long id,
                          final String code,
                          final Long memberId,
                          final Long memberName,
                          final String couponType) {
        this.id = id;
        this.code = code;
        this.memberId = memberId;
        this.memberName = memberName;
        this.couponType = couponType;
    }
}
