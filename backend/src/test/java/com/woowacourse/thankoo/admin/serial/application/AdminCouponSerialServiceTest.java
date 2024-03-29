package com.woowacourse.thankoo.admin.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.member.exception.AdminNotFoundMemberException;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.admin.organization.exception.AdminInvalidOrganizationException;
import com.woowacourse.thankoo.admin.serial.application.dto.AdminCouponSerialRequest;
import com.woowacourse.thankoo.admin.serial.domain.AdminCouponSerialRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.application.OrganizationService;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponCouponSerialService 는 ")
@ApplicationTest
class AdminCouponSerialServiceTest {

    @Autowired
    private AdminCouponSerialService adminCouponSerialService;

    @Autowired
    private AdminCouponSerialRepository couponSerialRepository;

    @Autowired
    private AdminMemberRepository memberRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AdminOrganizationRepository organizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("쿠폰 시리얼을 생성할 때 ")
    @Nested
    class CreateCouponSerialTest {

        @DisplayName("쿠폰 시리얼을 요청 개수만큼 생성한다.")
        @Test
        void save() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Organization organization = saveDefaultOrganizationAndJoinedMember(member);

            adminCouponSerialService.save(new AdminCouponSerialRequest(
                    member.getId(), organization.getId(), "COFFEE", 5, NEO_TITLE, NEO_MESSAGE));
            List<CouponSerial> couponSerial = couponSerialRepository.findAll();

            assertThat(couponSerial).hasSize(5);
        }

        @DisplayName("코치를 찾지 못 할 경우 예외를 발생한다.")
        @Test
        void notFoundCoach() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Organization organization = saveDefaultOrganizationAndJoinedMember(member);

            assertThatThrownBy(() -> adminCouponSerialService.save(
                    new AdminCouponSerialRequest(member.getId() + 1, organization.getId(), "COFFEE", 5, NEO_TITLE,
                            NEO_MESSAGE)))
                    .isInstanceOf(AdminNotFoundMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("존재하지 않는 쿠폰 타입일 경우 예외를 발생한다.")
        @Test
        void notFoundCouponType() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Organization organization = saveDefaultOrganizationAndJoinedMember(member);

            assertThatThrownBy(
                    () -> adminCouponSerialService.save(
                            new AdminCouponSerialRequest(member.getId(), organization.getId(), "NOOP", 5, NEO_TITLE,
                                    NEO_MESSAGE)))
                    .isInstanceOf(InvalidCouponContentException.class)
                    .hasMessage("존재하지 않는 쿠폰 타입입니다.");
        }

        @DisplayName("조직이 존재하지 않을 경우 예외를 발생한다.")
        @Test
        void notFoundOrganization() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThatThrownBy(
                    () -> adminCouponSerialService.save(
                            new AdminCouponSerialRequest(member.getId(), 1L, "COFFEE", 5, NEO_TITLE, NEO_MESSAGE)))
                    .isInstanceOf(AdminInvalidOrganizationException.class)
                    .hasMessage("조직을 찾을 수 없습니다.");
        }

        @DisplayName("조직에 회원이 가입하지 않은 경우 예외를 발생한다.")
        @Test
        void notJoinedOrganizationMember() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Member otherMember = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));
            Organization organization = saveDefaultOrganizationAndJoinedMember(otherMember);

            assertThatThrownBy(
                    () -> adminCouponSerialService.save(
                            new AdminCouponSerialRequest(member.getId(), organization.getId(), "COFFEE", 5, NEO_TITLE, NEO_MESSAGE)))
                    .isInstanceOf(AdminInvalidOrganizationException.class)
                    .hasMessage("조직에 가입되지 않은 회원입니다.");
        }

        private Organization saveDefaultOrganizationAndJoinedMember(Member member) {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            organizationService.join(member.getId(), new OrganizationJoinRequest(organization.getCode().getValue()));
            return organization;
        }
    }
}
