package com.woowacourse.thankoo.heart.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("HeartRepository 는 ")
@RepositoryTest
class HeartRepositoryTest {

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("보낸 사람과 받은 사람으로 마음을 조회한다.")
    @Test
    void findBySenderIdAndReceiverId() {
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        heartRepository.save(Heart.start(huni.getId(), skrr.getId()));
        Heart heart = heartRepository.findBySenderIdAndReceiverId(huni.getId(), skrr.getId()).get();
        assertAll(
                () -> assertThat(heart.getCount()).isEqualTo(1),
                () -> assertThat(heart.isLast()).isTrue()
        );
    }

    @DisplayName("보낸 사람으로 연달아 보낼 수 없는 마음을 조회한다")
    @Test
    void findBySenderIdAndLast() {
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));

        heartRepository.save(Heart.start(huni.getId(), skrr.getId()));
        heartRepository.save(Heart.start(huni.getId(), lala.getId()));
        List<Heart> hearts = heartRepository.findBySenderIdAndLast(huni.getId(), true);
        assertThat(hearts).hasSize(2);
    }

    @DisplayName("보낸 사람으로 연달아 보낼 수 없는 마음을 조회한다")
    @Test
    void findByReceiverIdAndLast() {
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));

        heartRepository.save(Heart.start(skrr.getId(), huni.getId()));
        heartRepository.save(Heart.start(lala.getId(), huni.getId()));
        List<Heart> hearts = heartRepository.findByReceiverIdAndLast(huni.getId(), true);
        assertThat(hearts).hasSize(2);
    }
}
