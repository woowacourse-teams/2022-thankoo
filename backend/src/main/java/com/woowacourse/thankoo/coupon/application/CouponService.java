package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.CouponQueryRepository;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatusGroup;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveAll(final Long senderId, final CouponRequest couponRequest) {
        validateMember(senderId, couponRequest.getReceiverIds());
        couponRepository.saveAll(couponRequest.toEntities(senderId));
    }

    private void validateMember(final Long senderId, final List<Long> receiverIds) {
        if (!isExistMembers(senderId, receiverIds)) {
            throw new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER);
        }
    }

    private boolean isExistMembers(final Long senderId, final List<Long> receiverIds) {
        return memberRepository.existsById(senderId)
                && memberRepository.countByIdIn(receiverIds) == receiverIds.size();
    }

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
}
