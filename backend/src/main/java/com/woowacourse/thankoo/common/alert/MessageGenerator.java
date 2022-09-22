package com.woowacourse.thankoo.common.alert;

public class MessageGenerator {

    private static final String USER_TEMPLATE = "USER_ID = %s\n";
    private static final String EXCEPTION_TITLE = "[ EXCEPTION ]\n";
    private static final String ALARM_FAILED_TITLE = "[ ALARM FAILED ]\n";
    private static final String EXCEPTION_TEMPLATE = "%s %s %s (line : %d)";

    private MessageGenerator() {
    }

    public static String generate(final String user, final ExceptionWrapper exceptionWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(USER_TEMPLATE, user));
        exceptionAppender(exceptionWrapper, stringBuilder);
        return stringBuilder.toString();
    }

    public static String generateFailedAlarmMessage(final SlackAlarmFailedEvent slackAlarmFailedEvent) {
        return ALARM_FAILED_TITLE + slackAlarmFailedEvent;
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
