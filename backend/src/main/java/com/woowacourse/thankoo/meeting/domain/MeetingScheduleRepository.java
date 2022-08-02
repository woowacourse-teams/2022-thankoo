package com.woowacourse.thankoo.meeting.domain;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetingScheduleRepository extends MeetingRepository {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Meeting m SET m.status = ? WHERE m.date = ? AND m.status = ?", nativeQuery = true)
    void expire(String updatedStatus, LocalDate date, String originStatus);
}
