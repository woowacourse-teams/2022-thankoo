package com.woowacourse.thankoo.admin.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_2;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_3;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.serial.domain.AdminCouponSerialRepository;
import com.woowacourse.thankoo.admin.serial.presentation.dto.AdminCouponSerialResponse;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.application.OrganizationService;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponSerialQueryService 는 ")
@ApplicationTest
class AdminCouponSerialQueryServiceTest {

    @Autowired
    private AdminCouponSerialQueryService adminCouponSerialQueryService;

    @Autowired
    private AdminCouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("회원의 id로 쿠폰 시리얼을 모두 조회한다.")
    @Test
    void getByMemberId() {
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
        organizationService.join(huni.getId(), new OrganizationJoinRequest(organization.getCode().getValue()));
        organizationService.join(hoho.getId(), new OrganizationJoinRequest(organization.getCode().getValue()));

        couponSerialRepository.saveAll(
                List.of(
                        new CouponSerial(organization.getId(), SERIAL_1, huni.getId(), COFFEE, NOT_USED, NEO_TITLE,
                                NEO_MESSAGE),
                        new CouponSerial(organization.getId(), SERIAL_2, huni.getId(), COFFEE, NOT_USED, NEO_TITLE,
                                NEO_MESSAGE),
                        new CouponSerial(organization.getId(), SERIAL_3, hoho.getId(), COFFEE, NOT_USED, NEO_TITLE,
                                NEO_MESSAGE)
                ));

        List<AdminCouponSerialResponse> responses = adminCouponSerialQueryService.getByMemberId(huni.getId());

        assertThat(responses).hasSize(2);
    }
}
