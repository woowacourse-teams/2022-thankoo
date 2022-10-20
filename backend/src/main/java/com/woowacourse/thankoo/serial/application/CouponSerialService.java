package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.serial.application.dto.SerialCodeRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialContent;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class CouponSerialService {

    private final CouponSerialRepository couponSerialRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;

    public void use(final Long memberId, final SerialCodeRequest serialCodeRequest) {
        Member receiver = getMemberById(memberId);
        CouponSerial couponSerial = getSerialByCode(serialCodeRequest.getSerialCode());
        Organization organization = getOrganization(couponSerial.getOrganizationId());
        validateMemberInOrganization(receiver, organization);
        couponSerial.use();

        couponRepository.save(coupon(organization.getId(), receiver, couponSerial));
    }

    private CouponSerial getSerialByCode(final String serialCode) {
        return couponSerialRepository.findBySerialCodeValue(serialCode)
                .orElseThrow(() -> new InvalidCouponSerialException(ErrorType.NOT_FOUND_COUPON_SERIAL));
    }

    private Organization getOrganization(final Long organizationId) {
        return organizationRepository.findWithMemberById(organizationId)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
    }

    private void validateMemberInOrganization(final Member member, final Organization organization) {
        if (!organization.containsMember(member)) {
            throw new InvalidOrganizationException(ErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private Coupon coupon(final Long organizationId, final Member receiver, final CouponSerial couponSerial) {
        return new Coupon(
                organizationId,
                couponSerial.getSenderId(),
                receiver.getId(),
                couponContent(couponSerial),
                CouponStatus.NOT_USED);
    }

    private static CouponContent couponContent(final CouponSerial couponSerial) {
        CouponSerialContent content = couponSerial.getContent();
        return new CouponContent(
                couponSerial.getCouponSerialType().getValue(),
                content.getTitle(),
                content.getMessage());
    }
}
