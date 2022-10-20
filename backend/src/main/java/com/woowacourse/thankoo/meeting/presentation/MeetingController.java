package com.woowacourse.thankoo.meeting.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.meeting.application.MeetingQueryService;
import com.woowacourse.thankoo.meeting.application.MeetingService;
import com.woowacourse.thankoo.meeting.presentation.dto.SimpleMeetingResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingQueryService meetingQueryService;
    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<SimpleMeetingResponse>> getMeetings(@AuthenticationPrincipal final Long memberId,
                                                                   @RequestParam("organization") final Long organizationId) {
        return ResponseEntity.ok(meetingQueryService.findMeetings(memberId, organizationId));
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<Void> complete(@AuthenticationPrincipal final Long memberId,
                                         @PathVariable final Long meetingId) {
        meetingService.complete(memberId, meetingId);
        return ResponseEntity.noContent().build();
    }
}
