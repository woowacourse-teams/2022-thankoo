package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponSelectCommand;
import com.woowacourse.thankoo.coupon.domain.CouponCount;
import com.woowacourse.thankoo.coupon.domain.CouponQueryRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponStatusGroup;
import com.woowacourse.thankoo.coupon.domain.MeetingProvider;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.coupon.domain.ReservationProvider;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponTotalResponse;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponQueryRepository couponQueryRepository;
    private final ReservationProvider reservationProvider;
    private final MeetingProvider meetingProvider;

    public List<CouponResponse> getReceivedCouponsByOrganization(final CouponSelectCommand couponSelectCommand) {
        List<String> statusNames = CouponStatusGroup.findStatusNames(couponSelectCommand.getStatus());
        return couponQueryRepository.findByOrganizationIdAndReceiverIdAndStatus(
                        couponSelectCommand.getOrganizationId(),
                        couponSelectCommand.getMemberId(),
                        statusNames
                ).stream()
                .map(CouponResponse::of)
                .collect(Collectors.toList());
    }

    public List<CouponResponse> getSentCouponsByOrganization(final Long organizationId, final Long senderId) {
        return couponQueryRepository.findByOrganizationIdAndSenderId(organizationId, senderId)
                .stream()
                .map(CouponResponse::of)
                .collect(Collectors.toList());
    }

    public CouponDetailResponse getCouponDetail(final Long memberId, final Long organizationId, final Long couponId) {
        MemberCoupon memberCoupon = getMemberCoupon(couponId);
        validateCouponOrganization(organizationId, memberCoupon);
        validateCouponOwner(memberId, memberCoupon);

        CouponStatus couponStatus = CouponStatus.of(memberCoupon.getStatus());
        if (couponStatus.isInReserveOrUsed()) {
            return getCouponDetailResponse(couponId, memberCoupon, couponStatus);
        }
        return CouponDetailResponse.of(memberCoupon);
    }

    private void validateCouponOrganization(final Long organizationId, final MemberCoupon memberCoupon) {
        if (!memberCoupon.isInOrganization(organizationId)) {
            throw new InvalidOrganizationException(ErrorType.COUPON_NOT_BELONGS_TO_ORGANIZATION);
        }
    }

    private MemberCoupon getMemberCoupon(final Long couponId) {
        return couponQueryRepository.findByCouponId(couponId)
                .orElseThrow(() -> new InvalidCouponException(ErrorType.NOT_FOUND_COUPON));
    }

    private void validateCouponOwner(final Long memberId, final MemberCoupon memberCoupon) {
        if (!memberCoupon.isOwner(memberId)) {
            throw new InvalidMemberException(ErrorType.INVALID_MEMBER);
        }
    }

    private CouponDetailResponse getCouponDetailResponse(final Long couponId,
                                                         final MemberCoupon memberCoupon,
                                                         final CouponStatus couponStatus) {
        if (couponStatus.isReserving()) {
            return CouponDetailResponse.from(memberCoupon, reservationProvider.getWaitingReservation(couponId));
        }
        return CouponDetailResponse.from(memberCoupon, meetingProvider.getMeetingByCouponId(couponId));
    }

    public CouponTotalResponse getCouponTotalCount(final Long memberId) {
        CouponCount sentCouponCount = couponQueryRepository.getSentCouponCount(memberId);
        CouponCount receivedCouponCount = couponQueryRepository.getReceivedCouponCount(memberId);
        return CouponTotalResponse.from(sentCouponCount, receivedCouponCount);
    }
}
