package com.woowacourse.thankoo.admin.authentication.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.admin.administrator.domain.Administrator;
import com.woowacourse.thankoo.admin.administrator.domain.AdministratorRepository;
import com.woowacourse.thankoo.admin.administrator.domain.AdministratorRole;
import com.woowacourse.thankoo.admin.authentication.application.dto.AdminSignInRequest;
import com.woowacourse.thankoo.admin.authentication.domain.PasswordEncryption;
import com.woowacourse.thankoo.admin.authentication.exception.InvalidLoginInformationException;
import com.woowacourse.thankoo.admin.authentication.presentation.dto.AdminSignInResponse;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AuthenticationService 는 ")
@ApplicationTest
class AdminAuthenticationServiceTest {

    @Autowired
    private AdminAuthenticationService authenticationService;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncryption passwordEncryption;

    @DisplayName("로그인을 할 떄 ")
    @Nested
    class SignInTest {

        @DisplayName("성공하면 토큰을 발급한다.")
        @Test
        void SingInSuccess() {
            String encryptedPassword = passwordEncryption.encrypt("password");
            Administrator administrator = new Administrator("name", encryptedPassword, AdministratorRole.ROOT);
            administratorRepository.save(administrator);

            AdminSignInResponse loginResponse = authenticationService.signIn(
                    new AdminSignInRequest("name", "password"));
            assertThat(loginResponse.getAccessToken()).isNotNull();
        }

        @DisplayName("로그인 정보가 유효하지 않으면 실패한다.")
        @Test
        void SingInFail() {
            Administrator administrator = new Administrator("name", "password", AdministratorRole.ROOT);
            administratorRepository.save(administrator);

            assertThatThrownBy(() -> authenticationService.signIn(new AdminSignInRequest("name", "wrongPassword")))
                    .isInstanceOf(InvalidLoginInformationException.class)
                    .hasMessage("올바르지 않은 비밀번호 또는 이름입니다.");
        }
    }
}
