package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.CouponQueryRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponStatusGroup;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.coupon.infrastructure.MeetingProvider;
import com.woowacourse.thankoo.coupon.infrastructure.ReservationProvider;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
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

    public List<CouponResponse> getReceivedCoupons(final Long receiverId, final String status) {
        List<String> statusNames = CouponStatusGroup.findStatusNames(status);
        return couponQueryRepository.findByReceiverIdAndStatus(receiverId, statusNames)
                .stream()
                .map(CouponResponse::of)
                .collect(Collectors.toList());
    }

    public List<CouponResponse> getSentCoupons(final Long senderId) {
        return couponQueryRepository.findBySenderId(senderId)
                .stream()
                .map(CouponResponse::of)
                .collect(Collectors.toList());
    }

    public CouponDetailResponse getCouponDetail(final Long memberId, final Long couponId) {
        MemberCoupon memberCoupon = getMemberCoupon(couponId);
        validateCouponOwner(memberId, memberCoupon);

        CouponStatus couponStatus = CouponStatus.of(memberCoupon.getStatus());
        if (couponStatus.isInReserve()) {
            return getCouponDetailResponse(couponId, memberCoupon, couponStatus);
        }
        return CouponDetailResponse.of(memberCoupon);
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
        if (couponStatus.isReserved()) {
            return CouponDetailResponse.from(memberCoupon, meetingProvider.getOnProgressMeeting(couponId));
        }
        return CouponDetailResponse.from(memberCoupon, reservationProvider.getWaitingReservation(couponId));
    }
}
