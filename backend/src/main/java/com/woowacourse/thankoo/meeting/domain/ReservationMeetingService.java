package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMeetingService implements ReservedMeetingCreator {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;

    @Override
    public void create(final Reservation reservation) {
        Coupon coupon = reservation.getCoupon();
        MeetingMembers meetingMembers =
                new MeetingMembers(
                        List.of(new MeetingMember(getMemberById(coupon.getSenderId()), reservation),
                                new MeetingMember(getMemberById(coupon.getReceiverId()), reservation))
                );
        MeetingTime meetingTime = reservation.getMeetingTime();
        Meeting meeting = new Meeting(meetingMembers, meetingTime.getTime(), TimeZoneType.ASIA_SEOUL,
                MeetingStatus.ON_PROGRESS, coupon);

        meetingRepository.save(meeting);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
