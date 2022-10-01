package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrganizationName {

    private static final int NAME_MAX_LENGTH = 12;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String value;

    public OrganizationName(String value) {
        value = value.strip();
        validateName(value);
        this.value = value;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > NAME_MAX_LENGTH) {
            throw new InvalidOrganizationException(ErrorType.INVALID_ORGANIZATION_NAME);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationName)) {
            return false;
        }
        OrganizationName that = (OrganizationName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "OrganizationName{" +
                "value='" + value + '\'' +
                '}';
    }
}
