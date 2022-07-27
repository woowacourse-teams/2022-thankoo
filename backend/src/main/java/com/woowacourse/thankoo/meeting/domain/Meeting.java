package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meeting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MeetingMembers meetingMembers;

    @Embedded
    private MeetingTime meetingTime;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public Meeting(final Long id,
                   final List<Member> members,
                   final MeetingTime meetingTime,
                   final MeetingStatus meetingStatus,
                   final Coupon coupon) {
        validateCouponOwners(members, coupon);
        this.id = id;
        this.meetingMembers = new MeetingMembers(convertToMeetingMember(members));
        this.meetingTime = meetingTime;
        this.meetingStatus = meetingStatus;
        this.coupon = coupon;
    }

    public Meeting(final List<Member> members,
                   final LocalDateTime meetingTime,
                   final TimeZoneType timeZone,
                   final MeetingStatus meetingStatus,
                   final Coupon coupon) {
        this(null,
                members,
                new MeetingTime(meetingTime.toLocalDate(), meetingTime, timeZone.getId()),
                meetingStatus,
                coupon);
    }

    private List<MeetingMember> convertToMeetingMember(final List<Member> members) {
        return members.stream()
                .map(member -> new MeetingMember(member, this))
                .collect(Collectors.toList());
    }

    private void validateCouponOwners(final List<Member> members, final Coupon coupon) {
        if (isMemberNotOwnsCoupon(members, coupon)) {
            throw new InvalidMeetingException(ErrorType.INVALID_MEETING_MEMBER);
        }
    }

    private boolean isMemberNotOwnsCoupon(final List<Member> members, final Coupon coupon) {
        return members.stream()
                .anyMatch(member -> !member.isOwner(coupon.getReceiverId(), coupon.getSenderId()));
    }
}
