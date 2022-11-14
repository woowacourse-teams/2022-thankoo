package com.woowacourse.thankoo.alarm.application.strategy;

import com.woowacourse.thankoo.alarm.domain.Emails;
import java.util.List;

public interface AlarmMemberProvider {

    Emails getReceiverEmails(List<Long> receiverIds);

    String getSenderName(String memberId);
}
