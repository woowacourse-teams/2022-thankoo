package com.woowacourse.thankoo.admin.administrator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdministratorRepository 는 ")
@RepositoryTest
class AdministratorRepositoryTest {

    @Autowired
    private AdministratorRepository administratorRepository;

    @DisplayName("관리자 이름을 통해 관리자 정보를 가져와야 한다.")
    @Test
    void getByName() {
        Administrator newAdministrator = administratorRepository.save(new Administrator("adminName",
                "password", AdministratorRole.ROOT));
        Administrator foundAdministrator = administratorRepository.findAdministratorByName("adminName");

        assertThat(newAdministrator).isEqualTo(foundAdministrator);
    }
}
