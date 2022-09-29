package com.woowacourse.thankoo.organization.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OrganizationCodeGenerator 는 ")
class OrganizationCodeGeneratorTest {

    @DisplayName("원하는 길이의 랜덤 코드를 만든다.")
    @Test
    void create() {
        OrganizationCodeGenerator organizationCodeGenerator = new OrganizationCodeGenerator();
        assertThat(organizationCodeGenerator.create(8)).hasSize(8);
    }
}
