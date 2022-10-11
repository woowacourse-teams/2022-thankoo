package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponCommand;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;

    @Deprecated
    public void saveAll(final Long senderId, final CouponRequest couponRequest) {
        Coupons coupons = Coupons.distribute(couponRequest.toEntities(senderId));
        couponRepository.saveAll(coupons.getValues());
    }

    public void saveAll(final CouponCommand couponCommand) {
        Member sender = getMember(couponCommand.getSenderId());
        List<Member> receivers = memberRepository.findByIdIn(couponCommand.getReceiverIds());
        validateMember(couponCommand.getReceiverIds(), receivers);

        Organization organization = getOrganization(couponCommand.getOrganizationId());
        validateOrganizationMembers(sender, receivers, organization);

        Coupons coupons = Coupons.distribute(couponCommand.toEntities());
        couponRepository.saveAll(coupons.getValues());
    }

    private void validateMember(final List<Long> receiverIds, final List<Member> receivers) {
        if (receiverIds.size() != receivers.size()) {
            throw new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER);
        }
    }

    private Organization getOrganization(final Long organizationId) {
        return organizationRepository.findWithMemberById(organizationId)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
    }

    // TODO : 조직 검증 머지되면 추가할 것
    public void useImmediately(final Long memberId, final Long organizationId, final Long couponId) {
        Member member = getMember(memberId);
        Organization organization = getOrganization(organizationId);
        validateOrganizationMembers(List.of(member), organization);
        Coupon coupon = getCoupon(couponId);
        coupon.useImmediately(member.getId(), organizationId);
    }

    private void validateOrganizationMembers(final Member sender,
                                             final List<Member> receivers,
                                             final Organization organization) {
        List<Member> members = new ArrayList<>();
        members.add(sender);
        members.addAll(receivers);
        validateOrganizationMembers(members, organization);
    }

    private void validateOrganizationMembers(final List<Member> members,
                                             final Organization organization) {
        if (!organization.containsMembers(members)) {
            throw new InvalidOrganizationException(ErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private Coupon getCoupon(final Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new InvalidCouponException(ErrorType.NOT_FOUND_COUPON));
    }
}
