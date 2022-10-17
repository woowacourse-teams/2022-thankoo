package com.woowacourse.thankoo.heart.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createThankooOrganization;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.heart.application.dto.HeartSendCommand;
import com.woowacourse.thankoo.heart.domain.Heart;
import com.woowacourse.thankoo.heart.domain.HeartRepository;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.application.OrganizationService;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("HeartService 는 ")
@ApplicationTest
class HeartServiceTest {

    @Autowired
    private HeartService heartService;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("마음을 보낼 때 ")
    @Nested
    class SendAndTest {

        @DisplayName("정상적인 요청일 경우 성공한다.")
        @Test
        void send() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(organization.getCode().getValue(), huni.getId(), skrr.getId());

            heartService.send(new HeartSendCommand(organization.getId(), huni.getId(), skrr.getId()));
            Heart heart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(huni.getId(),
                            skrr.getId(),
                            organization.getId())
                    .orElseThrow();

            assertThat(heart).isNotNull();
        }

        @DisplayName("상대가 마음을 보내면 내가 보낼 수 있다.")
        @Test
        void sendPlusCount() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(organization.getCode().getValue(), huni.getId(), skrr.getId());

            heartService.send(new HeartSendCommand(organization.getId(), huni.getId(), skrr.getId()));
            heartService.send(new HeartSendCommand(organization.getId(), skrr.getId(), huni.getId()));
            Heart heart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(huni.getId(),
                    skrr.getId(),
                    organization.getId()
            ).orElseThrow();

            assertThat(heart.isLast()).isFalse();
        }

        @DisplayName("다른 조직의 동일한 상대에게도 또 보낼 수 있다.")
        @Test
        void sendSameMemberDifferentOrganization() {
            Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Organization thankoo = organizationRepository.save(createThankooOrganization(organizationValidator));

            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(woowacourse.getCode().getValue(), huni.getId(), skrr.getId());
            join(thankoo.getCode().getValue(), huni.getId(), skrr.getId());

            heartService.send(new HeartSendCommand(woowacourse.getId(), huni.getId(), skrr.getId()));
            heartService.send(new HeartSendCommand(woowacourse.getId(), skrr.getId(), huni.getId()));
            heartService.send(new HeartSendCommand(woowacourse.getId(), huni.getId(), skrr.getId()));
            Heart woowacourseHuniHeart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(huni.getId(),
                    skrr.getId(),
                    woowacourse.getId()
            ).orElseThrow();

            Heart woowacourseSkrrHeart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(skrr.getId(),
                    huni.getId(),
                    woowacourse.getId()
            ).orElseThrow();

            heartService.send(new HeartSendCommand(thankoo.getId(), huni.getId(), skrr.getId()));
            heartService.send(new HeartSendCommand(thankoo.getId(), skrr.getId(), huni.getId()));

            Heart thankooHuniHeart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(huni.getId(),
                    skrr.getId(),
                    thankoo.getId()
            ).orElseThrow();

            Heart thankooSkrrHeart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(skrr.getId(),
                    huni.getId(),
                    thankoo.getId()
            ).orElseThrow();

            assertAll(
                    () -> assertThat(woowacourseHuniHeart.getCount()).isEqualTo(2),
                    () -> assertThat(woowacourseHuniHeart.isLast()).isTrue(),
                    () -> assertThat(woowacourseSkrrHeart.getCount()).isEqualTo(1),
                    () -> assertThat(woowacourseSkrrHeart.isLast()).isFalse(),
                    () -> assertThat(thankooHuniHeart.getCount()).isEqualTo(1),
                    () -> assertThat(thankooHuniHeart.isLast()).isFalse(),
                    () -> assertThat(thankooSkrrHeart.getCount()).isEqualTo(1),
                    () -> assertThat(thankooSkrrHeart.isLast()).isTrue()
            );
        }

        @DisplayName("연달아 두 번 보낼 경우 실패한다.")
        @Test
        void doubleSendFailed() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(organization.getCode().getValue(), huni.getId(), skrr.getId());

            heartService.send(new HeartSendCommand(organization.getId(), huni.getId(), skrr.getId()));
            heartService.send(new HeartSendCommand(organization.getId(), skrr.getId(), huni.getId()));

            assertThatThrownBy(
                    () -> heartService.send(new HeartSendCommand(organization.getId(), skrr.getId(), huni.getId())))
                    .isInstanceOf(InvalidHeartException.class)
                    .hasMessage("마음을 보낼 수 없습니다.");
        }

        @DisplayName("존재하지 않는 회원에게 보낼 경우 예외가 발생한다.")
        @Test
        void sendInvalidReceiverMember() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(organization.getCode().getValue(), skrr.getId());

            assertThatThrownBy(
                    () -> heartService.send(new HeartSendCommand(organization.getId(), skrr.getId(), 100L)))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("존재하지 않는 회원이 보낼 경우 예외가 발생한다.")
        @Test
        void sendInvalidSenderMember() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(organization.getCode().getValue(), skrr.getId());

            assertThatThrownBy(
                    () -> heartService.send(new HeartSendCommand(organization.getId(), 100L, skrr.getId())))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("같은 조직이 아닌 회원에게 보낼 경우 예외가 발생한다.")
        @Test
        void sendNotSameOrganizationMember() {
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(organization.getCode().getValue(), huni.getId());

            assertThatThrownBy(
                    () -> heartService.send(new HeartSendCommand(organization.getId(), huni.getId(), skrr.getId())))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직에 가입되지 않은 회원입니다.");
        }

        @DisplayName("다른 조직에 가입된 회원에게 보낼 경우 예외가 발생한다.")
        @Test
        void sendOtherOrganizationMember() {
            Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Organization thankoo = organizationRepository.save(createThankooOrganization(organizationValidator));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            join(woowacourse.getCode().getValue(), huni.getId());
            join(thankoo.getCode().getValue(), skrr.getId());

            assertThatThrownBy(
                    () -> heartService.send(new HeartSendCommand(woowacourse.getId(), huni.getId(), skrr.getId())))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직에 가입되지 않은 회원입니다.");
        }
    }

    @DisplayName("연달아 보낼 수 없는 보낸 마음과 응답할 수 있는 받은 마음을 모두 조회한다.")
    @Test
    void getEachHeartsLast() {
        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
        join(organization.getCode().getValue(), huni.getId(), skrr.getId(), lala.getId(), hoho.getId());

        heartService.send(new HeartSendCommand(organization.getId(), skrr.getId(), huni.getId()));
        heartService.send(new HeartSendCommand(organization.getId(), lala.getId(), huni.getId()));
        heartService.send(new HeartSendCommand(organization.getId(), hoho.getId(), huni.getId()));

        heartService.send(new HeartSendCommand(organization.getId(), huni.getId(), lala.getId()));

        HeartResponses heartResponses = heartService.getEachHeartsLast(organization.getId(), huni.getId());
        List<HeartResponse> sentHearts = heartResponses.getSent();
        List<HeartResponse> receivedHearts = heartResponses.getReceived();

        assertAll(
                () -> assertThat(sentHearts).hasSize(1),
                () -> assertThat(sentHearts).extracting("receiverId")
                        .containsExactly(lala.getId()),
                () -> assertThat(receivedHearts).hasSize(2),
                () -> assertThat(receivedHearts).extracting("senderId")
                        .containsExactly(skrr.getId(), hoho.getId())
        );
    }

    private void join(final String code, final Long... memberIds) {
        for (Long memberId : memberIds) {
            organizationService.join(memberId, new OrganizationJoinRequest(code));
        }
    }
}
