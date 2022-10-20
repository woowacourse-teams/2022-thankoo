package com.woowacourse.thankoo.member.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.member.application.MemberQueryService;
import com.woowacourse.thankoo.member.application.MemberService;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import com.woowacourse.thankoo.member.presentation.dto.OrganizationMemberResponse;
import com.woowacourse.thankoo.member.presentation.dto.ProfileImageUrlResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMember(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @PutMapping("/me/name")
    public ResponseEntity<Void> updateMemberName(@AuthenticationPrincipal final Long memberId,
                                                 @RequestBody final MemberNameRequest memberNameRequest) {
        memberService.updateMemberName(memberId, memberNameRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/profile-image")
    public ResponseEntity<Void> updateMemberProfileImage(@AuthenticationPrincipal final Long memberId,
                                                         @RequestBody final MemberProfileImageRequest memberProfileImageRequest) {
        memberService.updateMemberProfileImage(memberId, memberProfileImageRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile-images")
    public ResponseEntity<List<ProfileImageUrlResponse>> getMemberProfileImages() {
        return ResponseEntity.ok(memberService.getProfileImages());
    }

    @GetMapping
    public ResponseEntity<List<OrganizationMemberResponse>> getOrganizationMembersExcludeMe(
            @AuthenticationPrincipal final Long memberId,
            @RequestParam("organization") final Long organizationId) {
        List<OrganizationMemberResponse> organizationMembersExcludeMe = memberQueryService.getOrganizationMembersExcludeMe(
                memberId, organizationId);
        return ResponseEntity.ok(organizationMembersExcludeMe);
    }
}
