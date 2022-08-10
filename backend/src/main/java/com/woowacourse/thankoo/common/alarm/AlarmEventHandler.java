package com.woowacourse.thankoo.common.alarm;

import com.woowacourse.thankoo.common.alarm.cache.SlackUser;
import com.woowacourse.thankoo.common.alarm.cache.SlackUserRepository;
import com.woowacourse.thankoo.common.alarm.dto.SlackMessageEvent;
import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventHandler {

    private final MessageSender slackMessageSender;
    private final SlackUserRepository slackUserRepository;
    private final MemberRepository memberRepository;

    @EventListener
    public void send(SlackMessageEvent messageEvent) {
        Member member = getMember(messageEvent);
        SlackUser cachedSlackUser = slackUserRepository.getCachedSlackUser(member.getEmail().getValue());
        slackMessageSender.sendMessage(cachedSlackUser.getSlackUserId(), messageEvent.getAlarmMessage());
    }

    private Member getMember(final SlackMessageEvent messageEvent) {
        return memberRepository.findById(messageEvent.getMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorType.NOT_FOUND_MEMBER));
    }
}
