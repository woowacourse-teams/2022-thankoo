package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.util.List;
import java.util.Objects;
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
    private TimeUnit timeUnit;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public Meeting(final Long id,
                   final List<Member> members,
                   final TimeUnit timeUnit,
                   final MeetingStatus meetingStatus,
                   final Coupon coupon) {
        validateRightTime(timeUnit);
        validateCouponOwners(members, coupon);
        this.id = id;
        this.meetingMembers = new MeetingMembers(convertToMeetingMember(members));
        this.timeUnit = timeUnit;
        this.meetingStatus = meetingStatus;
        this.coupon = coupon;
    }

    public Meeting(final List<Member> members,
                   final TimeUnit timeUnit,
                   final MeetingStatus meetingStatus,
                   final Coupon coupon) {
        this(null,
                members,
                timeUnit,
                meetingStatus,
                coupon);
    }

    private void validateRightTime(final TimeUnit timeUnit) {
        if (!timeUnit.isAfterNow()) {
            throw new InvalidMeetingException(ErrorType.INVALID_MEETING_TIME);
        }
    }

    private void validateCouponOwners(final List<Member> members, final Coupon coupon) {
        if (isMemberNotOwnsCoupon(members, coupon)) {
            throw new InvalidMeetingException(ErrorType.INVALID_MEETING_MEMBER);
        }
    }

    private boolean isMemberNotOwnsCoupon(final List<Member> members, final Coupon coupon) {
        return members.stream()
                .anyMatch(member -> !member.hasSameId(List.of(coupon.getReceiverId(), coupon.getSenderId())));
    }

    private List<MeetingMember> convertToMeetingMember(final List<Member> members) {
        return members.stream()
                .map(member -> new MeetingMember(member, this))
                .collect(Collectors.toList());
    }

    public void complete(final Member member) {
        validateCompleteMember(member);
        validateMeetingStatusWhenComplete();
        meetingStatus = MeetingStatus.FINISHED;
        coupon.use();
    }

    private void validateCompleteMember(final Member member) {
        if (!isAttendee(member)) {
            throw new ForbiddenException(ErrorType.FORBIDDEN);
        }
    }

    private void validateMeetingStatusWhenComplete() {
        if (!meetingStatus.isOnProgress()) {
            throw new InvalidMeetingException(ErrorType.INVALID_MEETING_STATUS);
        }
    }

    private boolean isAttendee(final Member member) {
        return meetingMembers.have(member);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", meetingTime=" + timeUnit +
                ", meetingStatus=" + meetingStatus +
                ", coupon=" + coupon +
                '}';
    }
}
