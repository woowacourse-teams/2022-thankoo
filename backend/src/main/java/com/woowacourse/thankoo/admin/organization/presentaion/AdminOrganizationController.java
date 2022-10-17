package com.woowacourse.thankoo.admin.organization.presentaion;

import com.woowacourse.thankoo.admin.organization.application.AdminOrganizationService;
import com.woowacourse.thankoo.admin.organization.application.dto.AdminGetOrganizationsRequest;
import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequest;
import com.woowacourse.thankoo.admin.organization.presentaion.dto.AdminOrganizationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/organizations")
public class AdminOrganizationController {

    private final AdminOrganizationService adminOrganizationService;

    @PostMapping
    public ResponseEntity<Void> createOrganization(
            @RequestBody final AdminOrganizationCreationRequest organizationCreationRequest) {
        adminOrganizationService.createOrganization(organizationCreationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AdminOrganizationResponse>> getOrganizations(
            @ModelAttribute final AdminGetOrganizationsRequest getOrganizationsRequest) {
        List<AdminOrganizationResponse> organizations = adminOrganizationService.getOrganizations(
                getOrganizationsRequest);
        return ResponseEntity.ok(organizations);
    }
}
