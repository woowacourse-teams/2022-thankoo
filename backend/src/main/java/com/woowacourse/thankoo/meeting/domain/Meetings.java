package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Meetings {

    private final List<Meeting> values;

    public Meetings(final List<Meeting> values) {
        this.values = List.copyOf(values);
    }

    public List<Long> getMeetingIds() {
        return values.stream()
                .map(Meeting::getId)
                .collect(Collectors.toList());
    }

    public List<Coupon> getCoupons() {
        return values.stream()
                .map(Meeting::getCoupon)
                .collect(Collectors.toList());
    }

    public List<Member> getMembers() {
        return values.stream()
                .map(meeting -> meeting.getMeetingMembers().getValues())
                .flatMap(Collection::stream)
                .map(MeetingMember::getMember)
                .collect(Collectors.toList());
    }

    public boolean haveMeeting() {
        return !values.isEmpty();
    }
}
