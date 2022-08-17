package com.woowacourse.thankoo.heart.application;

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
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.domain.HeartQueryRepository;
import com.woowacourse.thankoo.heart.presentation.dto.ReceivedHeartResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("HeartQueryService 는 ")
@ApplicationTest
class HeartQueryServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private HeartQueryService heartQueryService;

    @Autowired
    private HeartService heartService;

    @Autowired
    private MemberRepository memberRepository;

    private HeartQueryRepository heartQueryRepository;

    @BeforeEach
    void setUp() {
        heartQueryRepository = new HeartQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("응답할 수 있는 받은 마음을 조회한다.")
    @Test
    void findBySenderIdAndReceiverId() {
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        heartService.send(skrr.getId(), new HeartRequest(huni.getId()));
        heartService.send(lala.getId(), new HeartRequest(huni.getId()));
        heartService.send(hoho.getId(), new HeartRequest(huni.getId()));

        heartService.send(huni.getId(), new HeartRequest(lala.getId()));

        List<ReceivedHeartResponse> receivedHearts = heartQueryService.getReceivedHeart(huni.getId());
        assertAll(
                () -> assertThat(receivedHearts).hasSize(2),
                () -> assertThat(receivedHearts).extracting("sender")
                        .extracting("id")
                        .containsExactly(hoho.getId(), skrr.getId())
        );
    }
}
