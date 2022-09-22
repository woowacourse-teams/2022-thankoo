package com.woowacourse.thankoo.member.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByIdNotOrderByNameAsc(Long id);

    long countByIdIn(List<Long> ids);

    Optional<Member> findBySocialId(String socialId);

    List<Member> findByIdIn(List<Long> id);
}
