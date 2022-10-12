package com.woowacourse.thankoo.admin.organization.presentaion;

import com.woowacourse.thankoo.admin.organization.application.AdminOrganizationService;
import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/organization")
public class AdminOrganizationController {

    private final AdminOrganizationService adminOrganizationService;

    @PostMapping
    public ResponseEntity<Void> createOrganization(
            @RequestBody final AdminOrganizationCreationRequest organizationCreationRequest) {
        adminOrganizationService.createOrganization(organizationCreationRequest);
        return ResponseEntity.ok().build();
    }
}
