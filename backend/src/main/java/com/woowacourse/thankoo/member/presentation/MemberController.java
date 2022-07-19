package com.woowacourse.thankoo.member.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.member.application.MemberService;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembersExcludeMe(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(memberService.getMembersExcludeMe(memberId));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMember(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }
}
