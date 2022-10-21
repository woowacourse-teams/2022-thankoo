package com.woowacourse.thankoo;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.domain.CodeGenerator;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationMembers;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialStatus;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!local")
public class InitData implements ApplicationRunner {

    private final OrganizationRepository organizationRepository;
    private final OrganizationValidator organizationValidator;
    private final MemberRepository memberRepository;
    private final CouponSerialRepository couponSerialRepository;

    @Override
    public void run(final ApplicationArguments args) {
        Member member = memberRepository.save(
                new Member("호호형님짱", "hoho@email.com", "sdasd", "/profile-image/user_corgi.svg"));

        CodeGenerator codeGenerator1 = length -> "00000001";
        CodeGenerator codeGenerator2 = length -> "00000002";
        CodeGenerator codeGenerator3 = length -> "00000003";

        Organization 양은이파 = Organization.create("양은이파", codeGenerator1, 500, organizationValidator);
        양은이파.join(member, new OrganizationMembers(new ArrayList<>()));

        Organization 삼거리파 = Organization.create("삼거리파", codeGenerator2, 200, organizationValidator);
        삼거리파.join(member, new OrganizationMembers(new ArrayList<>()));

        Organization 후이바보 = Organization.create("라라라파", codeGenerator3, 100, organizationValidator);
        후이바보.join(member, new OrganizationMembers(new ArrayList<>()));

        organizationRepository.saveAll(List.of(양은이파, 삼거리파, 후이바보));

        CouponSerial c1 = new CouponSerial(1L, "11111111", 1L, CouponSerialType.COFFEE,
                CouponSerialStatus.NOT_USED, "쌍화차 한잔 하자이",
                "먹자고잉");
        CouponSerial c2 = new CouponSerial(1L, "22222222", 1L, CouponSerialType.COFFEE,
                CouponSerialStatus.NOT_USED, "쌍화차 한잔 하자이",
                "먹자고잉");
        CouponSerial c3 = new CouponSerial(2L, "33333333", 1L, CouponSerialType.COFFEE,
                CouponSerialStatus.NOT_USED, "쌍화차 한잔 하자이",
                "먹자고잉");
        CouponSerial c4 = new CouponSerial(2L, "44444444", 1L, CouponSerialType.COFFEE,
                CouponSerialStatus.NOT_USED, "쌍화차 한잔 하자이",
                "먹자고잉");
        CouponSerial c5 = new CouponSerial(3L, "55555555", 1L, CouponSerialType.COFFEE,
                CouponSerialStatus.NOT_USED, "쌍화차 한잔 하자이",
                "먹자고잉");
        CouponSerial c6 = new CouponSerial(3L, "66666666", 1L, CouponSerialType.COFFEE,
                CouponSerialStatus.NOT_USED, "쌍화차 한잔 하자이",
                "먹자고잉");

        couponSerialRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6));
    }
}
