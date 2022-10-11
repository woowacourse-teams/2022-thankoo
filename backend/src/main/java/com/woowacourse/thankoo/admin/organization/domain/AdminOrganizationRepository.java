package com.woowacourse.thankoo.admin.organization.domain;

import com.woowacourse.thankoo.organization.domain.Organization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminOrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT DISTINCT o FROM Organization o "
            + "LEFT JOIN FETCH o.organizationMembers.values om "
            + "LEFT JOIN FETCH om.member m "
            + "WHERE o.id = :id")
    Optional<Organization> findWithMemberById(@Param("id") Long id);
}
