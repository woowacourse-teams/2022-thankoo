package com.woowacourse.thankoo.admin.administrator.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findAdministratorByName(String name);
}
