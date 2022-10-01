package com.woowacourse.thankoo.admin.administrator.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Optional<Administrator> findAdministratorByName(String name);
}
