package com.woowacourse.thankoo.alarm.domain;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class Email {

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$");

    private final String value;

    public Email(final String value) {
        validateEmail(value);
        this.value = value;
    }

    private void validateEmail(final String email) {
        if (email.isBlank() || !isValidPattern(email)) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_EMAIL);
        }
    }

    private boolean isValidPattern(final String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
