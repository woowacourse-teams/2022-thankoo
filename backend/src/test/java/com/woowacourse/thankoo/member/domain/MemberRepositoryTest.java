package com.woowacourse.thankoo.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MemberRepository 는")
@RepositoryTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        Member member = new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL);

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findByName() {
        Member member = new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL);
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByName_Value(HOHO_NAME);

        assertAll(
                () -> assertThat(foundMember).isNotEmpty(),
                () -> assertThat(foundMember.orElseThrow()).isEqualTo(member)
        );
    }

    @DisplayName("이름 순서대로 회원을 조회한다.")
    @Test
    void findAllByOrderByNameAsc() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        List<Member> members = memberRepository.findAllByIdNotOrderByNameAsc(member.getId());

        assertThat(members).extracting("name").containsExactly(new Name(HOHO_NAME), new Name(HUNI_NAME));
    }

    @DisplayName("id에 해당하는 회원 개수를 조회한다.")
    @Test
    void countByIdIn() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));

        long count = memberRepository.countByIdIn(List.of(lala.getId(), hoho.getId(), huni.getId()));
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("소셜 아이디로 회원을 조회한다.")
    @Test
    void findBySocialId() {
        Member member = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        Optional<Member> foundMember = memberRepository.findBySocialId(HOHO_SOCIAL_ID);

        assertAll(
                () -> assertThat(foundMember).isNotEmpty(),
                () -> assertThat(foundMember.orElseThrow()).isEqualTo(member)
        );
    }

    @DisplayName("회원들의 id로 회원 목록을 조회한다.")
    @Test
    void findByIdIn() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));

        List<Member> members = memberRepository.findByIdIn(List.of(lala.getId(), hoho.getId(), huni.getId()));

        assertThat(members).hasSize(3);
    }
}
