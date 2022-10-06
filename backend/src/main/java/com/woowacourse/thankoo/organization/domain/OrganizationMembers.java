package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrganizationMembers {

    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrganizationMember> values = new ArrayList<>();

    public OrganizationMembers(final List<OrganizationMember> values) {
        this.values = values;
    }

    public int size() {
        return values.size();
    }

    public void add(final OrganizationMember organizationMember) {
        values.add(organizationMember);
    }

    public boolean isAlreadyContains(final Organization organization) {
        return values.stream()
                .anyMatch(organizationMember -> organizationMember.isSameOrganization(organization));
    }

    public void toPreviousAccessed() {
        for (OrganizationMember value : values) {
            value.toPreviousAccessed();
        }
    }

    @Override
    public String toString() {
        return "OrganizationMembers{" +
                "values=" + values +
                '}';
    }
}
