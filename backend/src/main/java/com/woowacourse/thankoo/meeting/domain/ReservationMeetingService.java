package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationMeetingService implements ReservedMeetingCreator {

    private final MeetingRepository meetingRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void create(final Reservation reservation) {
        Coupon coupon = reservation.getCoupon();
        List<Member> members = List.of(getMemberById(coupon.getSenderId()), getMemberById(coupon.getReceiverId()));
        MeetingTime meetingTime = reservation.getMeetingTime();
        Meeting meeting = new Meeting(members, meetingTime.getTime(), TimeZoneType.ASIA_SEOUL,
                MeetingStatus.ON_PROGRESS, coupon);

        meetingRepository.save(meeting);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
