package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import com.woowacourse.thankoo.serial.domain.CouponSerialQueryRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponSerialQueryService {

    private final CouponSerialQueryRepository couponSerialQueryRepository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;

    public CouponSerialResponse getCouponSerialByCode(final CouponSerialRequest couponSerialRequest) {
        Member member = getMember(couponSerialRequest.getMemberId());
        Organization organization = getOrganization(couponSerialRequest.getOrganizationId());
        validateContainsMemberWithOrganization(member, organization);
        return CouponSerialResponse.from(getCouponSerialMember(couponSerialRequest.getCode()));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private Organization getOrganization(final Long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
    }

    private void validateContainsMemberWithOrganization(final Member member, final Organization organization) {
        if (!organization.containsMember(member)) {
            throw new InvalidOrganizationException(ErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
    }

    private CouponSerialMember getCouponSerialMember(final String code) {
        return couponSerialQueryRepository.findByCode(code)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
    }
}
