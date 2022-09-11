package com.woowacourse.thankoo.meeting.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            + "LEFT JOIN FETCH mt.meetingMembers.values mtm "
            + "LEFT JOIN FETCH mtm.member m "
            + "WHERE mt.coupon.id = :couponId ")
    Optional<Meeting> findTopByCouponId(@Param("couponId") Long couponId);

    @EntityGraph(attributePaths = "coupon", type = EntityGraphType.LOAD)
    List<Meeting> findAllByMeetingStatusAndTimeUnitTime(MeetingStatus status, LocalDateTime dateTime);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Meeting m SET m.meetingStatus = :status WHERE m.id IN (:meetingIds)")
    void updateMeetingStatus(@Param("status") MeetingStatus status, @Param("meetingIds") List<Long> meetingIds);

    @Query("SELECT DISTINCT mt FROM Meeting mt "
            + "LEFT JOIN FETCH mt.meetingMembers.values mtm "
            + "LEFT JOIN FETCH mtm.member m "
            + "WHERE mt.timeUnit.date = :date")
    List<Meeting> findAllByTimeUnit_Date(@Param("date") LocalDate date);
}
