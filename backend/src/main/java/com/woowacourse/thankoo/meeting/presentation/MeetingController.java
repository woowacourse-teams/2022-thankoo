package com.woowacourse.thankoo.meeting.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.meeting.application.MeetingService;
import com.woowacourse.thankoo.meeting.presentation.dto.MeetingResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/api/meetings")
    public ResponseEntity<List<MeetingResponse>> getMeetings(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(meetingService.findMeetings(memberId));
    }
}
