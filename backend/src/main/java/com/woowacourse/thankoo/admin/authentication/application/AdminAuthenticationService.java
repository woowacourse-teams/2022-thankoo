package com.woowacourse.thankoo.admin.authentication.application;

import com.woowacourse.thankoo.admin.administrator.domain.Administrator;
import com.woowacourse.thankoo.admin.administrator.domain.AdministratorRepository;
import com.woowacourse.thankoo.admin.authentication.application.dto.AdminLoginRequest;
import com.woowacourse.thankoo.admin.authentication.domain.PasswordEncryption;
import com.woowacourse.thankoo.admin.authentication.domain.TokenProvider;
import com.woowacourse.thankoo.admin.authentication.exception.InvalidLoginInformationException;
import com.woowacourse.thankoo.admin.authentication.presentation.dto.AdminLoginResponse;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminAuthenticationService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncryption passwordEncryption;
    private final TokenProvider accessTokenProvider;

    public AdminLoginResponse login(final AdminLoginRequest loginRequest) {
        Administrator administrator = administratorRepository.findAdministratorByName(loginRequest.getName())
                .orElseThrow(() -> new InvalidLoginInformationException(AdminErrorType.INVALID_LOGIN_INFORMATION));
        String encryptedPassword = passwordEncryption.encrypt(loginRequest.getPassword());
        validatePassword(encryptedPassword, administrator);
        return new AdminLoginResponse(administrator.getId(), accessTokenProvider.create(administrator.getId().toString()));
    }

    private void validatePassword(final String encryptedPassword, final Administrator administrator) {
        if (!administrator.isSamePassword(encryptedPassword)) {
            throw new InvalidLoginInformationException(AdminErrorType.INVALID_LOGIN_INFORMATION);
        }
    }
}
