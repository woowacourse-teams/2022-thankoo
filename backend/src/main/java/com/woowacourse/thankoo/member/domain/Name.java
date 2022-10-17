package com.woowacourse.thankoo.member.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Name {

    private static final int NAME_MAX_LENGTH = 5;

    @Column(name = "name", length = 50, nullable = false)
    private String value;

    public Name(String value) {
        value = value.strip();
        validateName(value);
        this.value = value;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > NAME_MAX_LENGTH) {
            throw new InvalidMemberException(ErrorType.INVALID_MEMBER_NAME);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name)) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Name{" +
                "value='" + value + '\'' +
                '}';
    }
}
