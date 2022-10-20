package com.woowacourse.thankoo.admin.common.exception;

import static com.woowacourse.thankoo.admin.common.exception.AdminErrorType.UNHANDLED_EXCEPTION;

import com.woowacourse.thankoo.admin.common.exception.dto.AdminErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.woowacourse.thankoo.admin"})
@Slf4j
public class AdminControllerAdvice {

    @ExceptionHandler(AdminBadRequestException.class)
    public ResponseEntity<AdminErrorResponse> adminBadRequestExceptionHandler(final AdminBadRequestException e) {
        log.warn("Admin Bad Request Exception", e);
        return ResponseEntity.badRequest().body(new AdminErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AdminUnAuthenticationException.class)
    public ResponseEntity<AdminErrorResponse> adminUnAuthenticationExceptionHandler(
            final AdminUnAuthenticationException e) {
        log.warn("Admin UnAuthentication Exception", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AdminErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AdminErrorResponse> unHandledExceptionHandler(final Exception e) {
        log.error("Not Expected Exception is Occurred", e);
        return ResponseEntity.internalServerError().body(new AdminErrorResponse(UNHANDLED_EXCEPTION.getMessage()));
    }
}
