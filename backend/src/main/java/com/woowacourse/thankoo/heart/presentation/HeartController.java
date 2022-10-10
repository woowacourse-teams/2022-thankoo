package com.woowacourse.thankoo.heart.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.heart.application.HeartService;
import com.woowacourse.thankoo.heart.application.dto.HeartSendRequest;
import com.woowacourse.thankoo.heart.presentation.dto.HeartRequest;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizations/{organizationId}/hearts")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long memberId,
                                     @PathVariable final Long organizationId,
                                     @RequestBody final HeartRequest heartRequest) {
        heartService.send(HeartSendRequest.from(organizationId, memberId, heartRequest));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<HeartResponses> getReceivedHearts(@AuthenticationPrincipal final Long memberId,
                                                            @PathVariable final Long organizationId) {
        return ResponseEntity.ok(heartService.getEachHeartsLast(organizationId, memberId));
    }
}
