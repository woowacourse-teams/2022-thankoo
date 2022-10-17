package com.woowacourse.thankoo.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MemberRepository 는 ")
@RepositoryTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("본인을 제외하고 이름 순서대로 회원을 조회한다.")
    @Test
    void findAllByOrderByNameAsc() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
        memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL));
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));

        List<Member> members = memberRepository.findAllByIdNotOrderByNameAsc(member.getId());

        assertThat(members).extracting("name")
                .containsExactly(new Name(HOHO_NAME), new Name(HUNI_NAME));
    }

    @DisplayName("id에 해당하는 회원 수를 조회한다.")
    @Test
    void countByIdIn() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL));

        long count = memberRepository.countByIdIn(List.of(lala.getId(), hoho.getId(), huni.getId()));

        assertThat(count).isEqualTo(3);
    }

    @DisplayName("소셜 아이디로 회원을 조회한다.")
    @Test
    void findBySocialId() {
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));

        Member member = memberRepository.findBySocialId(HOHO_SOCIAL_ID).orElseThrow();

        assertAll(
                () -> assertThat(member.getId()).isNotNull(),
                () -> assertThat(member.getSocialId()).isEqualTo(HOHO_SOCIAL_ID)
        );
    }

    @DisplayName("회원들의 id로 회원 목록을 조회한다.")
    @Test
    void findByIdIn() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

        List<Member> members = memberRepository.findByIdIn(List.of(lala.getId(), hoho.getId(), huni.getId()));

        assertAll(
                () -> assertThat(members).hasSize(3),
                () -> assertThat(members).extracting("name")
                        .containsExactly(new Name(LALA_NAME), new Name(HOHO_NAME), new Name(HUNI_NAME))
        );
    }
}
