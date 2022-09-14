package com.woowacourse.thankoo.alarm.application.strategy;

import java.util.List;

public interface AlarmMemberProvider {

    List<String> getReceiverEmails(final List<Long> receiverIds);

    String getSenderName(final String memberId);
}
