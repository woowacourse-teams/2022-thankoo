package com.woowacourse.thankoo.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("MemberService 는")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("SignIn 요청이 왔을 때 ")
    @Nested
    class SignInTest {

        @DisplayName("멤버가 존재하지 않으면 생성한다.")
        @Test
        void signInCreateMember() {
            Long id = memberService.createOrGet("hoho");

            assertAll(
                    () -> assertThat(id).isNotNull(),
                    () -> assertThat(memberRepository.findAll()).hasSize(1)
            );
        }

        @DisplayName("멤버가 존재하면 조회한다.")
        @Test
        void signInGetMember() {
            memberRepository.save(new Member("hoho"));
            Long id = memberService.createOrGet("hoho");

            assertAll(
                    () -> assertThat(id).isNotNull(),
                    () -> assertThat(memberRepository.findAll()).hasSize(1)
            );
        }
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}
