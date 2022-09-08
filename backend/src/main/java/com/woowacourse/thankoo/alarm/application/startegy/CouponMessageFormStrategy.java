package com.woowacourse.thankoo.alarm.application.startegy;

import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CouponMessageFormStrategy extends MemberMessageFormStrategy {

    private static final String SENDER = "보내는 이 : {0}";
    private static final String TITLE = "제목 : {0}";
    private static final String TITLE_LINK = "https://thankoo.co.kr/";
    private static final String TYPE = "쿠폰 종류 : {0}";
    private static final String COFFEE_PRETEXT = "\uD83D\uDC8C 커피 쿠폰이 도착했어요.";  // 💌
    private static final String MEAL_PRETEXT = "\uD83D\uDC8C 식사 쿠폰이 도착했어요.";    // 💌
    private static final Map<String, String> types = Map.of(
            "coffee", "커피☕",
            "meal", "식사\uD83C\uDF54");                                         // 🍔
    private static final int SENDER_ID_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int TYPE_INDEX = 2;
    private static final int CONTENT_SIZE = 3;

    public CouponMessageFormStrategy(final MemberRepository memberRepository) {
        super(memberRepository);
    }

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContent(alarm);
        List<String> receiverEmails = getReceiverEmails(alarm.getTargetIds());
        String senderName = getSenderName(alarm.getContents().get(SENDER_ID_INDEX));

        return Message.builder()
                .email(receiverEmails)
                .title(getTitle(alarm.getTitle()))
                .titleLink(TITLE_LINK)
                .content(MessageFormat.format(SENDER, senderName))
                .content(MessageFormat.format(TITLE, alarm.getContents().get(TITLE_INDEX)))
                .content(MessageFormat.format(TYPE, types.get(alarm.getContents().get(TYPE_INDEX))))
                .build();
    }

    private void validateContent(final Alarm alarm) {
        if (alarm.getContents().size() != CONTENT_SIZE) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_FORMAT);
        }
    }

    private String getTitle(final String title) {
        if (title.equalsIgnoreCase("COFFEE")) {
            return COFFEE_PRETEXT;
        }
        return MEAL_PRETEXT;
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.COUPON_SENT;
    }
}
