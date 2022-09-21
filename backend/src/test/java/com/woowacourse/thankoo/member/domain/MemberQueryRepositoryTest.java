package com.woowacourse.thankoo.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("MemberQueryRepository 는 ")
@RepositoryTest
class MemberQueryRepositoryTest {

    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberQueryRepository = new MemberQueryRepository(jdbcTemplate);
    }

    @DisplayName("회원이 존재하는지 확인할 때")
    @Nested
    class ExistedMember {

        @DisplayName("존재하면 true를 반환한다.")
        @Test
        void existedById() {
            Member member = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThat(memberQueryRepository.existsById(member.getId())).isTrue();
        }

        @DisplayName("존재하지 않으면 false를 반환한다.")
        @Test
        void notExistedById() {
            assertThat(memberQueryRepository.existsById(0L)).isFalse();
        }
    }
}
