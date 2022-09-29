package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import java.util.Collections;
import java.util.Objects;
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

    private static final int NAME_LENGTH = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrganizationName name;

    @Embedded
    private OrganizationCode code;

    @Embedded
    private OrganizationMembers organizationMembers;

    private Organization(final Long id,
                         final String name,
                         final OrganizationCode code,
                         final OrganizationMembers organizationMembers) {
        this.id = id;
        this.name = new OrganizationName(name);
        this.code = code;
        this.organizationMembers = organizationMembers;
    }

    private Organization(final String name,
                         final CodeGenerator codeGenerator) {
        this(null, name, OrganizationCode.create(codeGenerator), new OrganizationMembers(Collections.emptyList()));
    }

    public static Organization create(final String name,
                                      final CodeGenerator codeGenerator) {
        return new Organization(name, codeGenerator);
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
}
