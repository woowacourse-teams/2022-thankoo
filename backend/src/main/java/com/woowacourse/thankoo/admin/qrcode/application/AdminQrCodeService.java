package com.woowacourse.thankoo.admin.qrcode.application;

import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminLinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminSerialRequest;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminQrCodeService {

    private static final String URL = "http://api.qrserver.com/v1/create-qr-code/?data=https://thankoo.co.kr"
            + "?code={0}"
            + "&organization-id={1}"
            + "&size=300x300";

    private final OrganizationRepository organizationRepository;

    public List<AdminLinkResponse> getLinks(final AdminSerialRequest adminSerialRequest, final Long organizationId) {
        Organization organization = getOrganization(organizationId);
        return adminSerialRequest.getSerials().stream()
                .map(serial -> new AdminLinkResponse(MessageFormat.format(URL, serial, organization.getId())))
                .collect(Collectors.toList());
    }

    private Organization getOrganization(final Long adminSerialRequest) {
        return organizationRepository.findById(adminSerialRequest)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
    }
}
