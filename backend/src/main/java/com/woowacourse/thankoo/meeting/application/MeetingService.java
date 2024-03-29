package com.woowacourse.thankoo.meeting.application;

import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.ON_PROGRESS;

import com.woowacourse.thankoo.alarm.application.AlarmSender;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import com.woowacourse.thankoo.meeting.application.dto.MeetingMessage;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.meeting.domain.Meetings;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.domain.Members;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final AlarmSender alarmSender;

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

    public void complete(final LocalDateTime meetingTime) {
        Meetings meetings = new Meetings(
                meetingRepository.findAllByMeetingStatusAndTimeUnitTime(ON_PROGRESS, meetingTime));
        if (meetings.haveMeeting()) {
            Coupons coupons = new Coupons(meetings.getCoupons());
            meetingRepository.updateMeetingStatus(MeetingStatus.FINISHED, meetings.getMeetingIds());
            couponRepository.updateCouponStatus(CouponStatus.USED, coupons.getCouponIds());
        }
    }

    public void sendMessageTodayMeetingMembers(final LocalDate date) {
        Meetings meetings = new Meetings(meetingRepository.findAllByTimeUnitDate(date));
        Members members = new Members(meetings.getDistinctMembers());
        if (!members.isEmpty()) {
            alarmSender.send(MeetingMessage.of(members.getEmails()));
        }
    }
}
