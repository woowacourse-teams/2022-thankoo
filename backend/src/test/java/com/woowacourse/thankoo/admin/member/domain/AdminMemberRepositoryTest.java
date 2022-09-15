package com.woowacourse.thankoo.admin.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminMemberRepository 는 ")
@RepositoryTest
class AdminMemberRepositoryTest {

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @DisplayName("가입 날짜 검색 조건에 따른 회원들을 조회한다.")
    @Test
    void findAllByCreatedAtBetween() {
        LocalDateTime startDateTime = LocalDateTime.now();
        Member lala = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = adminMemberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        LocalDateTime endDateTime = LocalDateTime.now();

        List<Member> members = adminMemberRepository.findAllByCreatedAtBetween(startDateTime, endDateTime);
        assertAll(
                () -> assertThat(members).hasSize(2),
                () -> assertThat(members).extracting("name").containsOnly(lala.getName(), skrr.getName())
        );
    }
}
