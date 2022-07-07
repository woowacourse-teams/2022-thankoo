package com.woowacourse.thankoo.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("MemberRepository 는")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버를 저장한다.")
    @Test
    void save() {
        Member member = new Member(LALA_NAME);

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findByName() {
        Member member = new Member(LALA_NAME);
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByName(LALA_NAME);

        assertAll(
                () -> assertThat(foundMember).isNotEmpty(),
                () -> assertThat(foundMember.orElseThrow()).isEqualTo(member)
        );
    }

    @DisplayName("이름 순서대로 회원을 조회한다.")
    @Test
    void findAllByOrderByNameAsc() {
        Member member = memberRepository.save(new Member(LALA_NAME));
        memberRepository.save(new Member(HOHO_NAME));
        memberRepository.save(new Member(HUNI_NAME));

        List<Member> members = memberRepository.findAllByIdNotOrderByNameAsc(member.getId());

        assertThat(members).extracting("name").containsExactly(HOHO_NAME, HUNI_NAME);
    }
}
