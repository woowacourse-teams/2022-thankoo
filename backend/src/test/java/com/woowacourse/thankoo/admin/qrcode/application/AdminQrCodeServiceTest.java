package com.woowacourse.thankoo.admin.qrcode.application;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminLinkResponse;
import com.woowacourse.thankoo.admin.qrcode.presentation.dto.AdminSerialRequest;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminQrCodeService 는 ")
@ApplicationTest
class AdminQrCodeServiceTest {

    @Autowired
    private AdminQrCodeService qrCodeService;

    @Autowired
    private AdminOrganizationRepository organizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("QR 코드 링크를 생성할 때")
    @Nested
    class QrCodeLink {

        @DisplayName("조직이 존재하지 않으면 예외를 발생한다.")
        @Test
        void notFoundOrganization() {
            assertThatThrownBy(() -> qrCodeService.getLinks(new AdminSerialRequest(List.of(SERIAL_1, SERIAL_2)), 1L))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직을 찾을 수 없습니다.");
        }

        @DisplayName("조직이 존재하면 정상적으로 생성된다.")
        @Test
        void createQrCodeLink() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            List<AdminLinkResponse> links = qrCodeService.getLinks(
                    new AdminSerialRequest(List.of(SERIAL_1, SERIAL_2)), organization.getId());

            assertThat(links).hasSize(2);
        }
    }
}
