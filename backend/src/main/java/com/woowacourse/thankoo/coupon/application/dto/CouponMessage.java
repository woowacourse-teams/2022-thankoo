package com.woowacourse.thankoo.coupon.application.dto;

import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Name;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponMessage {

    private static final String SENDER = "보내는 이 : {0}";
    private static final String TITLE = "제목 : {0}";
    private static final String TITLE_LINK = "https://thankoo.co.kr/";
    private static final String TYPE = "쿠폰 종류 : {0}";
    private static final String COFFEE_PRETEXT = "\uD83D\uDC8C 커피 쿠폰이 도착했어요.";  // 💌
    private static final String MEAL_PRETEXT = "\uD83D\uDC8C 식사 쿠폰이 도착했어요.";    // 💌
    private static final Map<String, String> type = Map.of(
            "coffee", "커피☕",
            "meal", "식사\uD83C\uDF54");                                         // 🍔

    public static Message of(final Name name, final List<String> emails, final CouponContent couponContent) {
        return Message.builder()
                .email(emails)
                .title(getTitle(couponContent.getCouponType().getValue()))
                .titleLink(TITLE_LINK)
                .content(MessageFormat.format(SENDER, name.getValue()))
                .content(MessageFormat.format(TITLE, couponContent.getTitle()))
                .content(MessageFormat.format(TYPE, type.get(couponContent.getCouponType().getValue())))
                .build();
    }

    public static String getTitle(final String couponType) {
        if (couponType.equalsIgnoreCase("COFFEE")) {
            return COFFEE_PRETEXT;
        }
        return MEAL_PRETEXT;
    }
}
