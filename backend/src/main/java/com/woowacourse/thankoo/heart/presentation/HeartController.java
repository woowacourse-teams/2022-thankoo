package com.woowacourse.thankoo.heart.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.heart.application.HeartService;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hearts")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@AuthenticationPrincipal final Long memberId,
                                     @RequestBody final HeartRequest heartRequest) {
        heartService.send(memberId, heartRequest);
        return ResponseEntity.ok().build();
    }
}
