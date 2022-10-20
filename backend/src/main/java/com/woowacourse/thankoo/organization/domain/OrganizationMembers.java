package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public boolean containsOrganization(final Organization organization) {
        return values.stream()
                .anyMatch(organizationMember -> organizationMember.isSameOrganization(organization));
    }

    public boolean containsMember(final Member member) {
        return values.stream()
                .anyMatch(organizationMember -> organizationMember.isSameMember(member));
    }

    public void toPreviousAccessed() {
        for (OrganizationMember value : values) {
            value.toPreviousAccessed();
        }
    }

    public void switchLastAccessed(final Organization organization) {
        for (OrganizationMember value : values) {
            if (value.isSameOrganization(organization)) {
                value.toLastAccessed();
                continue;
            }
            value.toPreviousAccessed();
        }
    }

    public List<OrganizationMember> getExclude(final Member member) {
        return values.stream()
                .filter(value -> !value.isSameMember(member))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "OrganizationMembers{" +
                "values=" + values +
                '}';
    }
}
