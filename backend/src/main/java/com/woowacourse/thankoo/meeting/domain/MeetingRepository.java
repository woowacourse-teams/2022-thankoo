package com.woowacourse.thankoo.meeting.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT DISTINCT m FROM Meeting m LEFT JOIN FETCH m.meetingMembers.meetingMembers mm WHERE m.coupon.id = :couponId AND m.meetingStatus = :status")
    Optional<Meeting> findTopByCouponIdAndMeetingStatus(@Param("couponId") Long couponId, @Param("status") MeetingStatus status);
}
