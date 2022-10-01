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
public class OrganizationCode {

    private static final int DEFAULT_SIZE = 8;

    @Column(name = "code", length = 20, unique = true, nullable = false)
    private String value;

    private OrganizationCode(final String value) {
        validateCodeLength(value);
        this.value = value;
    }

    private void validateCodeLength(final String code) {
        if (code.length() != DEFAULT_SIZE) {
            throw new InvalidOrganizationException(ErrorType.INVALID_ORGANIZATION_CODE);
        }
    }

    public static OrganizationCode create(final CodeGenerator codeGenerator) {
        return new OrganizationCode(codeGenerator.create(DEFAULT_SIZE));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationCode)) {
            return false;
        }
        OrganizationCode that = (OrganizationCode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "OrganizationCode{" +
                "value='" + value + '\'' +
                '}';
    }
}
