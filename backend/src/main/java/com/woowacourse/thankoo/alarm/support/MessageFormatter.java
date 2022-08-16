package com.woowacourse.thankoo.alarm.support;

public class MessageFormatter {

    private static final char DELIM_START = '{';
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    public static String format(final String messagePattern, final Object ... args) {
        return arrayFormat(messagePattern, args);
    }

    private static String arrayFormat(final String messagePattern, final Object[] argArray) {
        if (messagePattern == null) {
            return null;
        }

        if (argArray == null) {
            return messagePattern;
        }

        int currentIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (int argLength = 0; argLength < argArray.length; argLength++) {

            int delimiterIndex = messagePattern.indexOf(DELIM_STR, currentIndex);
            if (isNoMoreDelimiter(delimiterIndex)) {
                return toStringNoMoreDelimiter(messagePattern, currentIndex, stringBuilder);
            } else {
                if (isEscapedDelimiter(messagePattern, delimiterIndex)) {
                    if (!isDoubleEscaped(messagePattern, delimiterIndex)) {
                        argLength--;
                        appendMessagePatternAndArg(messagePattern,
                                stringBuilder,
                                currentIndex,
                                delimiterIndex - 1,
                                DELIM_START);
                        currentIndex = delimiterIndex + 1;
                    } else {
                        appendMessagePatternAndArg(messagePattern,
                                stringBuilder,
                                currentIndex,
                                delimiterIndex - 1,
                                argArray[argLength]);
                        currentIndex = delimiterIndex + 2;
                    }
                } else {
                    appendMessagePatternAndArg(messagePattern,
                            stringBuilder,
                            currentIndex,
                            delimiterIndex,
                            argArray[argLength]);
                    currentIndex = delimiterIndex + 2;
                }
            }
        }
        stringBuilder.append(messagePattern, currentIndex, messagePattern.length());
        return stringBuilder.toString();
    }

    private static boolean isNoMoreDelimiter(final int delimiterIndex) {
        return delimiterIndex == -1;
    }

    private static String toStringNoMoreDelimiter(final String messagePattern,
                                                  final int currentIndex,
                                                  final StringBuilder stringBuilder) {
        if (currentIndex == 0) {
            return messagePattern;
        }
        stringBuilder.append(messagePattern, currentIndex, messagePattern.length());
        return stringBuilder.toString();
    }

    private static boolean isEscapedDelimiter(final String messagePattern, final int delimiterStartIndex) {
        if (delimiterStartIndex == 0) {
            return false;
        }
        char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
        return potentialEscape == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(final String messagePattern, final int delimiterStartIndex) {
        return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == ESCAPE_CHAR;
    }

    private static void appendMessagePatternAndArg(final String messagePattern,
                                                   final StringBuilder stringBuilder,
                                                   final int startIndex,
                                                   final int endIndex,
                                                   final Object arg) {
        stringBuilder.append(messagePattern, startIndex, endIndex);
        stringBuilder.append(arg.toString());
    }
}
