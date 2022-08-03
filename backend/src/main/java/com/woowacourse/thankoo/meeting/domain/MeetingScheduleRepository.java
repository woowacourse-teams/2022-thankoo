package com.woowacourse.thankoo.meeting.domain;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetingScheduleRepository extends MeetingRepository {

    /**
     * 해당 메서드는 스케줄 관련 작업에서 사용한다.
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Meeting m SET m.status = ? WHERE m.date = ? AND m.status = ?", nativeQuery = true)
    void updateMeetingStatus(String updatedStatus, LocalDate date, String originStatus);
}
