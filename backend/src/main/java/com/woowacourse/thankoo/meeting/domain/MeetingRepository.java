package com.woowacourse.thankoo.meeting.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT DISTINCT mt FROM Meeting mt "
            + "LEFT JOIN FETCH mt.meetingMembers.meetingMembers mtm "
            + "LEFT JOIN FETCH mtm.member m "
            + "WHERE mt.coupon.id = :couponId "
            + "AND mt.meetingStatus = :status")
    Optional<Meeting> findTopByCouponIdAndMeetingStatus(@Param("couponId") Long couponId, @Param("status") MeetingStatus status);
}
