package com.woowacourse.thankoo.admin.member.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAll();
}
