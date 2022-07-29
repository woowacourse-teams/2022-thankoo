package com.woowacourse.thankoo.meeting.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Optional<Meeting> findTopByCoupon_IdAndMeetingStatus(Long couponId, MeetingStatus status);
}
