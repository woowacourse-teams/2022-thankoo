package com.woowacourse.thankoo.admin.serial.application;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.member.exception.AdminNotFoundMemberException;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.admin.organization.exception.AdminInvalidOrganizationException;
import com.woowacourse.thankoo.admin.serial.application.dto.AdminCouponSerialRequest;
import com.woowacourse.thankoo.admin.serial.domain.AdminCodeCreator;
import com.woowacourse.thankoo.admin.serial.domain.AdminCouponSerialQueryRepository;
import com.woowacourse.thankoo.admin.serial.domain.AdminSerialCodes;
import com.woowacourse.thankoo.admin.serial.excepion.AdminInvalidCouponSerialException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCouponSerialService {

    private final AdminCouponSerialQueryRepository couponSerialQueryRepository;
    private final CouponSerialRepository couponSerialRepository;
    private final MemberRepository memberRepository;
    private final AdminOrganizationRepository organizationRepository;
    private final AdminCodeCreator adminCodeCreator;

    public void save(final AdminCouponSerialRequest couponSerialRequest) {
        Member member = getMember(couponSerialRequest.getMemberId());
        validateContainsMemberWithOrganization(member, getOrganization(couponSerialRequest.getOrganizationId()));
        AdminSerialCodes adminSerialCodes = AdminSerialCodes.of(couponSerialRequest.getQuantity(), adminCodeCreator);
        validateDuplicate(adminSerialCodes);
        couponSerialRepository.saveAll(create(couponSerialRequest, adminSerialCodes, member.getId()));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new AdminNotFoundMemberException(AdminErrorType.NOT_FOUND_MEMBER));
    }

    private Organization getOrganization(final Long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new AdminInvalidOrganizationException(AdminErrorType.NOT_FOUND_ORGANIZATION));
    }

    private void validateContainsMemberWithOrganization(final Member member, final Organization organization) {
        if (!organization.containsMember(member)) {
            throw new AdminInvalidOrganizationException(AdminErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
    }

    private void validateDuplicate(final AdminSerialCodes adminSerialCodes) {
        if (couponSerialQueryRepository.existsBySerialCodeValue(adminSerialCodes.getSerialCodeValues())) {
            throw new AdminInvalidCouponSerialException(AdminErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private List<CouponSerial> create(final AdminCouponSerialRequest couponSerialRequest,
                                      final AdminSerialCodes adminSerialCodes,
                                      final Long senderId) {
        List<SerialCode> values = adminSerialCodes.getValues();
        return values.stream()
                .map(code -> couponSerialRequest.from(code, senderId))
                .collect(Collectors.toList());
    }
}
