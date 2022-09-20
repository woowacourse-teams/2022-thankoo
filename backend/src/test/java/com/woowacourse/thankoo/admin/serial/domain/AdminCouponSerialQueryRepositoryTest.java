package com.woowacourse.thankoo.admin.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_2;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_3;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("AdminCouponSerialRepository 는 ")
@RepositoryTest
class AdminCouponSerialQueryRepositoryTest {

    private AdminCouponSerialQueryRepository adminCouponSerialQueryRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @BeforeEach
    void setUp() {
        adminCouponSerialQueryRepository = new AdminCouponSerialQueryRepository(
                new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("코치의 id로 가진 쿠폰 시리얼을 모두 조회한다.")
    @Test
    void getByMemberId() {
        Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
        Member member = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        couponSerialRepository.save(
                new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
        couponSerialRepository.save(
                new CouponSerial(SERIAL_2, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
        couponSerialRepository.save(
                new CouponSerial(SERIAL_3, member.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

        List<CouponSerialMember> couponSerialMembers = adminCouponSerialQueryRepository.findByMemberId(sender.getId());

        assertThat(couponSerialMembers).hasSize(2);
    }

//    @DisplayName("코드가 존재하는지 확인한다.")
//    @Test
//    void existsByCode() {
//        Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
//        Member member = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
//
//        couponSerialRepository.save(
//                new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
//        couponSerialRepository.save(
//                new CouponSerial(SERIAL_2, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
//        couponSerialRepository.save(
//                new CouponSerial(SERIAL_3, member.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
//
//        List<CouponSerialMember> couponSerialMembers = adminCouponSerialQueryRepository.findByMemberId(sender.getId());
//
//        assertThat(couponSerialMembers).hasSize(2);
//    }

    @DisplayName("코드가 존재하는지 확인한다.")
    @Test
    void existsByCode() {
        Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

        couponSerialRepository.save(
                new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
        couponSerialRepository.save(
                new CouponSerial(SERIAL_2, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
        couponSerialRepository.save(
                new CouponSerial(SERIAL_3, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

        assertThat(adminCouponSerialQueryRepository.existsBySerialCodeValue(List.of(SERIAL_1, SERIAL_2))).isTrue();
    }

    @DisplayName("코드가 존재하지 않는지 확인한다.")
    @Test
    void notExistsByCode() {
        Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

        couponSerialRepository.save(
                new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));
        couponSerialRepository.save(
                new CouponSerial(SERIAL_2, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

        assertThat(adminCouponSerialQueryRepository.existsBySerialCodeValue(List.of(SERIAL_3))).isFalse();
    }
}
