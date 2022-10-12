package com.woowacourse.thankoo.alarm.application.strategy.coupon;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.application.strategy.CouponMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.infrastructure.AlarmLinkGenerator;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponMealMessageFormStrategy extends CouponMessageFormStrategy {

    private static final String MEAL_PRETEXT = "\uD83D\uDC8C 식사 쿠폰이 도착했어요.";
    private static final String ACCEPT_TITLE_LINK = "/organization/{0}";
    private static final String MEAL_TYPE = "식사\uD83C\uDF54";

    private final AlarmMemberProvider alarmMemberProvider;
    private final AlarmLinkGenerator alarmLinkGenerator;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContent(alarm);
        List<String> receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContentAt(SENDER_ID_INDEX));

        return Message.builder()
                .email(receiverEmails)
                .title(MEAL_PRETEXT)
                .titleLink(alarmLinkGenerator.createUrl(
                        MessageFormat.format(ACCEPT_TITLE_LINK, alarm.getOrganizationId())))
                .content(MessageFormat.format(SENDER, senderName))
                .content(MessageFormat.format(TITLE, alarm.getContentAt(TITLE_INDEX)))
                .content(MessageFormat.format(TYPE, MEAL_TYPE))
                .build();
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.COUPON_SENT_MEAL;
    }
}
