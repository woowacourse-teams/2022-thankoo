package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Name;
import java.text.MessageFormat;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponMessage {

    public static final String SENDER = "보내는 이 : {0}";
    public static final String TITLE = "제목 : {0}";
    public static final String TYPE = "쿠폰 종류 : {0}";
    public static final String PRETEXT = "\uD83D\uDC8C 커피 쿠폰이 도착했어요.";

    public static Message of(final Name name, final List<String> emails, final CouponContent couponContent) {
        return new Message.Builder()
                .email(emails)
                .title(PRETEXT)
                .content(MessageFormat.format(SENDER, name.getValue()))
                .content(MessageFormat.format(TITLE, couponContent.getTitle()))
                .content(MessageFormat.format(TYPE, couponContent.getCouponType().getValue()))
                .build();
    }
}
