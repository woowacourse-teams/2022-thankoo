package com.woowacourse.thankoo.common.alert;

public class MessageGenerator {

    private static final String REQUEST_TITLE = "[ REQUEST FROM ]\n";
    private static final String REQUEST_TEMPLATE = "%s %s\n";
    private static final String EXCEPTION_TITLE = "[ EXCEPTION ]\n";
    private static final String EXCEPTION_TEMPLATE = "%s %s %s (line : %d)";

    public static String generate(final RequestWrapper requestWrapper, final ExceptionWrapper exceptionWrapper) {
        StringBuilder stringBuilder = new StringBuilder();

        requestAppender(requestWrapper, stringBuilder);
        exceptionAppender(exceptionWrapper, stringBuilder);
        return stringBuilder.toString();
    }

    private static void requestAppender(final RequestWrapper requestWrapper, final StringBuilder stringBuilder) {
        stringBuilder.append(REQUEST_TITLE)
                .append(String.format(REQUEST_TEMPLATE, requestWrapper.getRequestURI(),
                        requestWrapper.getRequestMethod()));
        requestWrapper.getHeaderMap()
                .forEach((key, value) -> stringBuilder.append(String.format(REQUEST_TEMPLATE, key, value)));
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
