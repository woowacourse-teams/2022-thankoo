package com.woowacourse.thankoo.admin.meeting.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminMeetingRepository extends JpaRepository<Meeting, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Meeting m SET m.meetingStatus = :updatingStatus WHERE m.meetingStatus = :status AND m.coupon IN (:coupons)")
    void updateMeetingStatus(MeetingStatus status, MeetingStatus updatingStatus, List<Coupon> coupons);
}
