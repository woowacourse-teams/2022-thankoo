package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByCode(OrganizationCode code);

    boolean existsByName(OrganizationName name);

    @Query("SELECT DISTINCT o FROM Organization o "
            + "LEFT JOIN FETCH o.organizationMembers.values om "
            + "WHERE o.code.value = :code")
    Optional<Organization> findByCodeValue(@Param("code") String code);

    @Query("SELECT om FROM OrganizationMember om "
            + "WHERE om.member = :member "
            + "ORDER BY om.orderNumber")
    List<OrganizationMember> findOrganizationMembersByMember(@Param("member") Member member);
}
