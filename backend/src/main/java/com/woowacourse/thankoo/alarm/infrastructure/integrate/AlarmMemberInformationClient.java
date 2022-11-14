package com.woowacourse.thankoo.alarm.infrastructure.integrate;

import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.domain.Emails;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.domain.Members;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmMemberInformationClient implements AlarmMemberProvider {

    private final MemberRepository memberRepository;

    @Override
    public Emails getReceiverEmails(final List<Long> receiverIds) {
        Members members = new Members(memberRepository.findByIdIn(receiverIds));
        return Emails.from(members.getEmails());
    }

    @Override
    public String getSenderName(final String memberId) {
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
