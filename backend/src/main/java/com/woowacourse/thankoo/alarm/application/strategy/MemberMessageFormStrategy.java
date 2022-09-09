package com.woowacourse.thankoo.alarm.application.strategy;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.domain.Members;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MemberMessageFormStrategy implements MessageFormStrategy {

    private final MemberRepository memberRepository;

    protected List<String> getReceiverEmails(final List<Long> receiverIds) {
        return new Members(memberRepository.findByIdIn(receiverIds)).getEmails();
    }

    protected String getSenderName(final String memberId) {
        try {
            return getMember(Long.valueOf(memberId)).getName().getValue();
        } catch (NumberFormatException e) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_FORMAT);
        }
    }

    private Member getMember(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
