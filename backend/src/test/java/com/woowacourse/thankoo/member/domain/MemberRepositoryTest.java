package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
        Member member = new Member("lala");

        Member actual = memberRepository.save(member);

        assertThat(actual).isEqualTo(member);
    }
}
