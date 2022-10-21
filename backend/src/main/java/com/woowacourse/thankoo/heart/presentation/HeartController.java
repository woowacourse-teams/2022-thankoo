package com.woowacourse.thankoo.heart.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.heart.application.HeartService;
import com.woowacourse.thankoo.heart.application.dto.HeartSelectCommand;
import com.woowacourse.thankoo.heart.application.dto.HeartSendCommand;
import com.woowacourse.thankoo.heart.presentation.dto.HeartExchangeResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartRequest;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hearts")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long memberId,
                                     @RequestBody final HeartRequest heartRequest) {
        heartService.send(HeartSendCommand.from(memberId, heartRequest));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<HeartExchangeResponse> getHeart(@AuthenticationPrincipal final Long memberId,
                                                          @RequestParam("receiver") final Long receiverId,
                                                          @RequestParam("organization") final Long organizationId) {
        return ResponseEntity.ok(
                heartService.getSentReceivedHeart(new HeartSelectCommand(memberId, receiverId, organizationId)));
    }

    @GetMapping("/me")
    public ResponseEntity<HeartResponses> getReceivedHearts(@AuthenticationPrincipal final Long memberId,
                                                            @RequestParam("organization") final Long organizationId) {
        return ResponseEntity.ok(heartService.getEachHeartsLast(organizationId, memberId));
    }
}
