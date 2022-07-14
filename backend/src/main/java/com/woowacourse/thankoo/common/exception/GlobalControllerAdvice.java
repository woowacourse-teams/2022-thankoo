package com.woowacourse.thankoo.common.exception;

import static com.woowacourse.thankoo.common.exception.ErrorType.UNHANDLED_EXCEPTION;

import com.woowacourse.thankoo.common.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException e) {
        log.error("Bad Request Exception", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(UnauthorizedException e) {
        log.error("Unauthorized Exception", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandledExceptionHandler(Exception e) {
        log.error("Not Expected Exception is Occured", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(UNHANDLED_EXCEPTION.getCode(),
                UNHANDLED_EXCEPTION.getMessage()));
    }
}
