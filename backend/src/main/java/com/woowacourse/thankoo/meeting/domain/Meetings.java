package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Meetings {

    private final List<Meeting> meetings;

    public Meetings(final List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public List<Long> getMeetingIds() {
        return meetings.stream()
                .map(Meeting::getId)
                .collect(Collectors.toList());
    }

    public List<Coupon> getCoupons() {
        return meetings.stream()
                .map(Meeting::getCoupon)
                .collect(Collectors.toList());
    }

    public List<Member> getMembers() {
        return meetings.stream()
                .map(meeting -> meeting.getMeetingMembers().getMeetingMembers())
                .flatMap(Collection::stream)
                .map(MeetingMember::getMember)
                .collect(Collectors.toList());
    }
}
