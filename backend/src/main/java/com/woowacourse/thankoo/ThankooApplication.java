package com.woowacourse.thankoo;

import com.woowacourse.thankoo.organization.domain.CodeGenerator;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ThankooApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThankooApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class Init {

        private final OrganizationRepository organizationRepository;
        private final OrganizationValidator organizationValidator;

        @PostConstruct
        public void set() {
            CodeGenerator codeGenerator1 = length -> "00000001";
            CodeGenerator codeGenerator2 = length -> "00000002";
            CodeGenerator codeGenerator3 = length -> "00000003";

            organizationRepository.saveAll(
                    List.of(Organization.create("양은희파", codeGenerator1, 500, organizationValidator),
                            Organization.create("삼거리파", codeGenerator2, 200, organizationValidator),
                            Organization.create("라라라파", codeGenerator3, 100, organizationValidator)));
        }
    }
}
