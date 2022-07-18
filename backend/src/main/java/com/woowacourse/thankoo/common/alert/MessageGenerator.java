package com.woowacourse.thankoo.common.alert;

public class MessageGenerator {

    private static final String USER_TEMPLATE = "USER_ID = %s\n";
    private static final String EXCEPTION_TITLE = "[ EXCEPTION ]\n";
    private static final String EXCEPTION_TEMPLATE = "%s %s %s (line : %d)";

    public static String generate(final String user, final ExceptionWrapper exceptionWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(USER_TEMPLATE, user));
        exceptionAppender(exceptionWrapper, stringBuilder);
        return stringBuilder.toString();
    }

    private static StringBuilder exceptionAppender(final ExceptionWrapper exceptionWrapper,
                                                   final StringBuilder stringBuilder) {
        return stringBuilder.append(EXCEPTION_TITLE)
                .append(String.format(EXCEPTION_TEMPLATE, exceptionWrapper.getExceptionClassName(),
                        exceptionWrapper.getExceptionMethodName(),
                        exceptionWrapper.getMessage(),
                        exceptionWrapper.getExceptionLineNumber()));
    }
}
