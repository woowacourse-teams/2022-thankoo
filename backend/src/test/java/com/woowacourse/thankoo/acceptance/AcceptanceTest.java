package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;

import com.woowacourse.thankoo.common.support.DataClearExtension;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ExtendWith(DataClearExtension.class)
abstract class AcceptanceTest {

    @Autowired
    protected OrganizationRepository organizationRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    protected void 기본_조직이_생성됨() {
        Organization organization1 = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE,
                30,
                new OrganizationValidator(organizationRepository));

        Organization organization2 = Organization.create(ORGANIZATION_THANKOO,
                length -> ORGANIZATION_THANKOO_CODE,
                30,
                new OrganizationValidator(organizationRepository));
        organizationRepository.save(organization1);
        organizationRepository.save(organization2);
    }
}
