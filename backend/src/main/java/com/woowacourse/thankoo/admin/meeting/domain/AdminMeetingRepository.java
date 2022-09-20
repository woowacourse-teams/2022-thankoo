package com.woowacourse.thankoo.admin.meeting.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminMeetingRepository extends JpaRepository<Meeting, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Meeting m SET m.meetingStatus = :updatingStatus WHERE m.meetingStatus = :status AND m.coupon IN (:coupons)")
    void updateMeetingStatus(@Param("status") MeetingStatus status,
                             @Param("updatingStatus") MeetingStatus updatingStatus,
                             @Param("coupons") List<Coupon> coupons);
}
