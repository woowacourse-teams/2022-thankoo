package com.woowacourse.thankoo.common.alert;

import lombok.Getter;

@Getter
public class ExceptionWrapper {

    private final String exceptionClassName;
    private final String exceptionMethodName;
    private final int exceptionLineNumber;
    private final String message;

    public ExceptionWrapper(final String exceptionClassName,
                            final String exceptionMethodName,
                            final int exceptionLineNumber,
                            final String message) {
        this.exceptionClassName = exceptionClassName;
        this.exceptionMethodName = exceptionMethodName;
        this.exceptionLineNumber = exceptionLineNumber;
        this.message = message;
    }
}
