package com.woowacourse.thankoo.meeting.application;

import com.woowacourse.thankoo.meeting.domain.MeetingQueryRepository;
import com.woowacourse.thankoo.meeting.presentation.dto.SimpleMeetingResponse;
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
        return meetingQueryRepository.findMeetingsByMemberId(memberId).stream()
                .map(SimpleMeetingResponse::of)
                .collect(Collectors.toList());
    }
}
