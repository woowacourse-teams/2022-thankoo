package com.woowacourse.thankoo.meeting.application;

import com.woowacourse.thankoo.alarm.AlarmMessage;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.support.AlarmMessageRequest;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.Meetings;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.domain.Members;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;

    public void complete(final Long memberId, final Long meetingId) {
        Meeting meeting = getMeetingById(meetingId);
        Member member = getMemberById(memberId);

        meeting.complete(member);
    }

    private Meeting getMeetingById(final Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new InvalidMeetingException(ErrorType.NOT_FOUND_MEETING));
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    public void sendMessageTodayMeetingMembers() {
        Meetings meetings = new Meetings(meetingRepository.findAllByTimeUnit_Date(LocalDate.now()));
        Members members = new Members(meetings.getMembers());
        AlarmManager.setResources(new AlarmMessageRequest(members.getEmails(), AlarmMessage.MEETING));
    }
}
