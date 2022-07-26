package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.reservation.domain.MeetingTime;
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

}
