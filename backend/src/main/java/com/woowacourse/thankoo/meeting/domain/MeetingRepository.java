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

    Optional<Meeting> findTopByCouponIdAndMeetingStatus(Long couponId, MeetingStatus status);

    @EntityGraph(attributePaths = "coupon", type = EntityGraphType.LOAD)
    List<Meeting> findAllByMeetingStatusAndMeetingTime_Date(MeetingStatus status, LocalDate date);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Meeting m SET m.meetingStatus = :status WHERE m.id IN (:meetingIds)")
    void updateMeetingStatus(@Param("status") MeetingStatus status, @Param("meetingIds") List<Long> meetingIds);
}
