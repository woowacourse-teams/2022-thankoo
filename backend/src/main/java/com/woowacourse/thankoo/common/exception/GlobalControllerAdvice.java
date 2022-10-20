package com.woowacourse.thankoo.common.exception;

import static com.woowacourse.thankoo.common.exception.ErrorType.UNHANDLED_EXCEPTION;

import com.woowacourse.thankoo.common.alert.SlackLogger;
import com.woowacourse.thankoo.common.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.woowacourse.thankoo.alarm", "com.woowacourse.thankoo.authentication",
        "com.woowacourse.thankoo.common", "com.woowacourse.thankoo.coupon", "com.woowacourse.thankoo.heart",
        "com.woowacourse.thankoo.meeting", "com.woowacourse.thankoo.member", "com.woowacourse.thankoo.organization",
        "com.woowacourse.thankoo.reservation", "com.woowacourse.thankoo.serial"})
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final BadRequestException e) {
        log.warn("Bad Request Exception", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(final UnauthorizedException e) {
        log.warn("Unauthorized Exception", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(final ForbiddenException e) {
        log.warn("Forbidden Exception", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> springValidationExceptionHandler(final MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("MethodArgumentNotValidException : {}", message);
        return ResponseEntity.badRequest().body(new ErrorResponse(ErrorType.REQUEST_EXCEPTION.getCode(), message));
    }

    @SlackLogger
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandledExceptionHandler(final Exception e) {
        log.error("Not Expected Exception is Occurred", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(UNHANDLED_EXCEPTION.getCode(),
                UNHANDLED_EXCEPTION.getMessage()));
    }
}
