package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationMembers;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("CouponSerialQueryRepository 는 ")
@RepositoryTest
class CouponSerialQueryRepositoryTest {

    private CouponSerialQueryRepository couponSerialQueryRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        couponSerialQueryRepository = new CouponSerialQueryRepository(new NamedParameterJdbcTemplate(dataSource));
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("시리얼 코드로 쿠폰 시리얼을 조회한다.")
    @Test
    void findByCode() {
        Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, SKRR_IMAGE_URL));
        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());
        organization.join(member, organizationMembers);
        organizationRepository.flush();

        couponSerialRepository.save(
                new CouponSerial(organization.getId(), SERIAL_1, member.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

        CouponSerialMember couponSerialMember = couponSerialQueryRepository.findByCode(SERIAL_1).orElseThrow();

        assertThat(couponSerialMember.getOrganizationId()).isEqualTo(organization.getId());
        assertThat(couponSerialMember.getSenderName()).isEqualTo(NEO_NAME);
    }
}
