package com.woowacourse.thankoo.meeting.application;

import com.woowacourse.thankoo.meeting.application.dto.MeetingQueryCondition;
import com.woowacourse.thankoo.meeting.domain.MeetingCoupon;
import com.woowacourse.thankoo.meeting.domain.MeetingQueryRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.meeting.presentation.dto.SimpleMeetingResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingQueryService {

    private final MeetingQueryRepository meetingQueryRepository;

    public List<SimpleMeetingResponse> findMeetings(final Long memberId) {
        MeetingQueryCondition meetingQueryCondition = new MeetingQueryCondition(memberId,
                LocalDateTime.now(),
                MeetingStatus.ON_PROGRESS.name());
        return meetingQueryRepository.findMeetingsByMemberIdAndTimeAndStatus(meetingQueryCondition).stream()
                .map(SimpleMeetingResponse::of)
                .collect(Collectors.toList());
    }
}
