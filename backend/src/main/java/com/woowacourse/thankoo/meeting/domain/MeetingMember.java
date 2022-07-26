package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.util.Objects;
import javax.persistence.Entity;
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
@Table(name = "meeting_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public MeetingMember(final Long id, final Member member, final Reservation reservation) {
        this.id = id;
        this.member = member;
        this.reservation = reservation;
    }

    public MeetingMember(final Member member, final Reservation reservation) {
        this(null, member, reservation);
    }

    public boolean isSameMemberId(final Long memberId) {
        return member.isSameId(memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetingMember)) {
            return false;
        }
        MeetingMember that = (MeetingMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MeetingMember{" +
                "id=" + id +
                ", member=" + member.getId() +
                ", reservation=" + reservation.getId() +
                '}';
    }
}
