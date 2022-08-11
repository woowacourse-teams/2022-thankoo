package com.woowacourse.thankoo.meeting.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT DISTINCT mt FROM Meeting mt "
            + "LEFT JOIN FETCH mt.meetingMembers.meetingMembers mtm "
            + "LEFT JOIN FETCH mtm.member m "
            + "WHERE mt.coupon.id = :couponId "
            + "AND mt.meetingStatus = :status")
    Optional<Meeting> findTopByCouponIdAndMeetingStatus(@Param("couponId") Long couponId, @Param("status") MeetingStatus status);

    @EntityGraph(attributePaths = "coupon", type = EntityGraphType.LOAD)
    List<Meeting> findAllByMeetingStatusAndTimeUnit_Date(MeetingStatus status, LocalDate date);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Meeting m SET m.meetingStatus = :status WHERE m.id IN (:meetingIds)")
    void updateMeetingStatus(@Param("status") MeetingStatus status, @Param("meetingIds") List<Long> meetingIds);

    @Query("SELECT DISTINCT mt FROM Meeting mt "
            + "LEFT JOIN FETCH mt.meetingMembers.meetingMembers mtm "
            + "LEFT JOIN FETCH mtm.member m ")
    List<Meeting> findAllByTimeUnit_Date(LocalDate date);
}
