package com.woowacourse.thankoo;

import com.woowacourse.thankoo.organization.domain.CodeGenerator;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class InitData implements ApplicationRunner {

    private final OrganizationRepository organizationRepository;
    private final OrganizationValidator organizationValidator;

    @Override
    public void run(final ApplicationArguments args) {
        CodeGenerator codeGenerator1 = length -> "00000001";
        CodeGenerator codeGenerator2 = length -> "00000002";
        CodeGenerator codeGenerator3 = length -> "00000003";

        organizationRepository.saveAll(
                List.of(Organization.create("양은희파", codeGenerator1, 500, organizationValidator),
                        Organization.create("삼거리파", codeGenerator2, 200, organizationValidator),
                        Organization.create("라라라파", codeGenerator3, 100, organizationValidator)));
    }
}
