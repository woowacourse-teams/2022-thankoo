package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationMeetingService implements ReservedMeetingCreator {

    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void create(final Reservation reservation) {
        Coupon coupon = reservation.getCoupon();
        List<Member> members = memberRepository.findAllById(List.of(coupon.getSenderId(), coupon.getReceiverId()));
        Meeting meeting = new Meeting(members, reservation.getTimeUnit(), MeetingStatus.ON_PROGRESS, coupon);
        meetingRepository.save(meeting);
    }
}
