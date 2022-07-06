package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.CouponHistory;
import com.woowacourse.thankoo.coupon.domain.CouponHistoryQueryRepository;
import com.woowacourse.thankoo.coupon.domain.CouponHistoryRepository;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponHistoryResponse;
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
public class CouponHistoryService {

    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponHistoryQueryRepository couponHistoryQueryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(final Long senderId, final CouponRequest couponRequest) {
        validateMember(senderId, couponRequest.getReceiverId());
        CouponHistory couponHistory = couponHistoryRepository.save(couponRequest.toEntity(senderId));
        return couponHistory.getId();
    }

    private void validateMember(final Long senderId, final Long receiverId) {
        if (isExistMembers(senderId, receiverId)) {
            throw new InvalidMemberException();
        }
    }

    private boolean isExistMembers(final Long senderId, final Long receiverId) {
        return !memberRepository.existsById(senderId)
                || !memberRepository.existsById(receiverId);
    }

    public List<CouponHistoryResponse> getReceivedCoupons(final Long receiverId) {
        return couponHistoryQueryRepository.findByReceiverId(receiverId)
                .stream()
                .map(CouponHistoryResponse::of)
                .collect(Collectors.toList());
    }
}
