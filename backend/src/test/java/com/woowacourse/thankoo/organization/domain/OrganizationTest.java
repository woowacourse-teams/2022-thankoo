package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

@DisplayName("Organization 은 ")
class OrganizationTest {

    private OrganizationValidator validator;

    @BeforeEach
    void setUp() {
        validator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(validator).validate(any(Organization.class));
    }

    @DisplayName("새 조직을 생성할 때 ")
    @Nested
    class CreateTest {

        @DisplayName("조직원이 0명이다.")
        @Test
        void organizationMemberEmpty() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    100,
                    validator);
            assertThat(organization.getOrganizationMembers().getValues()).isEmpty();
        }

        @DisplayName("코드가 8글자이다.")
        @Test
        void codeSize() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    100,
                    validator);
            assertThat(organization.getCode().getValue()).hasSize(8);
        }

        @DisplayName("조직 정원이 적절할 경우 성공한다.")
        @ParameterizedTest
        @ValueSource(ints = {10, 500})
        void limitedSizeSuccess(final int value) {
            assertDoesNotThrow(
                    () -> Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(), value,
                            validator));
        }

        @DisplayName("조직 정원이 적절하지 않을 경우 실패한다.")
        @ParameterizedTest
        @ValueSource(ints = {9, 501})
        void limitedSizeFailed(final int value) {
            assertThatThrownBy(
                    () -> Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(), value,
                            validator))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("유효하지 않은 조직의 인원입니다.");
        }
    }

    @DisplayName("조직에 참여할 떄 ")
    @Nested
    class JoinTest {

        @DisplayName("제한 인원이 초과되면 예외가 발생한다.")
        @Test
        void sizeOverFlow() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    10, validator);
            for (long i = 1; i <= 10; i++) {
                Member member = new Member(i, "na" + i, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL);
                organization.join(member, new OrganizationMembers(new ArrayList<>()));
            }

            assertThatThrownBy(
                    () -> organization.join(new Member(11L, HUNI_NAME, HUNI_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL),
                            new OrganizationMembers(new ArrayList<>())))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직에 더이상 참여할 수 없습니다.");
        }

        @DisplayName("이미 참여 중이면 예외가 발생한다.")
        @Test
        void alreadyJoined() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    10, validator);

            Member member = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL);
            organization.join(member, new OrganizationMembers(new ArrayList<>()));

            assertThatThrownBy(
                    () -> organization.join(new Member(1L, HUNI_NAME, HUNI_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL),
                            organization.getOrganizationMembers()))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직에 이미 참여중입니다.");
        }

        @DisplayName("참여에 성공한다.")
        @Test
        void join() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    10, validator);
            assertDoesNotThrow(
                    () -> organization.join(new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL),
                            new OrganizationMembers(new ArrayList<>())));
        }
    }

    @DisplayName("회원 가입여부를 확인할 때 ")
    @Nested
    class ContainsTest {

        @DisplayName("모든 회원이 가입되어있으면 true 를 반환한다.")
        @Test
        void containsAllMembers() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    10, validator);
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL);
            Member lala = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
            organization.join(huni, new OrganizationMembers(new ArrayList<>()));
            organization.join(lala, new OrganizationMembers(new ArrayList<>()));

            assertThat(organization.containsMembers(List.of(huni, lala))).isTrue();
        }

        @DisplayName("한 명이라도 가입되어있지 않으면 false 를 반환한다.")
        @Test
        void noContainsAnyMembers() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(),
                    10, validator);
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL);
            Member lala = new Member(2L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
            organization.join(huni, new OrganizationMembers(new ArrayList<>()));

            assertThat(organization.containsMembers(List.of(huni, lala))).isFalse();
        }
    }
}
