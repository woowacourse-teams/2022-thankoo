package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organization_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrganizationMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @Column(name = "last_accessed", nullable = false)
    private boolean lastAccessed;

    public OrganizationMember(final Long id,
                              final Member member,
                              final Organization organization,
                              final int orderNumber,
                              final boolean lastAccessed) {
        this.id = id;
        this.member = member;
        this.organization = organization;
        this.orderNumber = orderNumber;
        this.lastAccessed = lastAccessed;
    }

    public OrganizationMember(final Member member,
                              final Organization organization,
                              final int orderNumber,
                              final boolean lastAccessed) {
        this(null, member, organization, orderNumber, lastAccessed);
    }

    public boolean isSameOrganization(final Organization organization) {
        return this.organization.equals(organization);
    }

    public boolean isSameMember(final Member member) {
        return this.member.equals(member);
    }

    public void toPreviousAccessed() {
        if (lastAccessed) {
            lastAccessed = false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMember)) {
            return false;
        }
        OrganizationMember that = (OrganizationMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrganizationMember{" +
                "id=" + id +
                ", member=" + member.getId() +
                ", organization=" + organization.getId() +
                ", orderNumber=" + orderNumber +
                ", lastAccessed=" + lastAccessed +
                '}';
    }
}
