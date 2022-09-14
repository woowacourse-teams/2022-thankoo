package com.woowacourse.thankoo.admin.member.presentation;

import com.woowacourse.thankoo.admin.common.search.dto.AdminDateFilterRequest;
import com.woowacourse.thankoo.admin.member.application.AdminMemberService;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping
    public ResponseEntity<List<AdminMemberResponse>> getMembers(
            @ModelAttribute final AdminDateFilterRequest dateFilterRequest) {
        return ResponseEntity.ok(adminMemberService.getMembers(dateFilterRequest));
    }
}
