package com.woowacourse.thankoo.organization.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.organization.application.OrganizationQueryService;
import com.woowacourse.thankoo.organization.application.OrganizationService;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final OrganizationQueryService organizationQueryService;

    @GetMapping("/me")
    public ResponseEntity<List<OrganizationResponse>> getMyOrganizations(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(organizationQueryService.getMemberOrganizations(memberId));
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@AuthenticationPrincipal final Long memberId,
                                     @RequestBody final OrganizationJoinRequest organizationJoinRequest) {
        organizationService.join(memberId, organizationJoinRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{organizationId}/access")
    public ResponseEntity<Void> access(@AuthenticationPrincipal final Long memberId,
                                       @PathVariable final Long organizationId) {
        organizationService.access(memberId, organizationId);
        return ResponseEntity.ok().build();
    }
}
