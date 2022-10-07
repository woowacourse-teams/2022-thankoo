package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.util.Collections;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organization")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Organization extends BaseEntity {

    private static final int MIN_LIMITED_SIZE = 10;
    private static final int MAX_LIMITED_SIZE = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrganizationName name;

    @Embedded
    private OrganizationCode code;

    @Column(name = "limited_size", nullable = false)
    private int limitedSize;

    @Embedded
    private OrganizationMembers organizationMembers;

    private Organization(final Long id,
                         final OrganizationName organizationName,
                         final OrganizationCode code,
                         final int limitedSize,
                         final OrganizationMembers organizationMembers) {
        validateMaxLimitedSize(limitedSize);
        this.id = id;
        this.name = organizationName;
        this.code = code;
        this.limitedSize = limitedSize;
        this.organizationMembers = organizationMembers;
    }

    private void validateMaxLimitedSize(final int limitedSize) {
        if (limitedSize < MIN_LIMITED_SIZE || limitedSize > MAX_LIMITED_SIZE) {
            throw new InvalidOrganizationException(ErrorType.INVALID_ORGANIZATION_LIMITED_SIZE);
        }
    }

    private Organization(final String name,
                         final CodeGenerator codeGenerator,
                         final int limitedSize) {
        this(null,
                new OrganizationName(name),
                OrganizationCode.create(codeGenerator),
                limitedSize,
                new OrganizationMembers(Collections.emptyList()));
    }

    public static Organization create(final String name,
                                      final CodeGenerator codeGenerator,
                                      final int limitedSize,
                                      final OrganizationValidator organizationValidator) {
        Organization organization = new Organization(name, codeGenerator, limitedSize);
        organizationValidator.validate(organization);
        return organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        Organization that = (Organization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name=" + name +
                ", code=" + code +
                ", limitedSize=" + limitedSize +
                ", organizationMembers=" + organizationMembers +
                '}';
    }
}