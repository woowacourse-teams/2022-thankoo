package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
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
    public List<Long> saveAll(final Long senderId, final CouponRequest couponRequest) {
        validateMember(senderId, couponRequest.getReceiverIds());
        List<CouponHistory> couponHistories = couponHistoryRepository.saveAll(couponRequest.toEntities(senderId));
        return toCouponHistoryIds(couponHistories);
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

    private List<Long> toCouponHistoryIds(final List<CouponHistory> couponHistories) {
        return couponHistories.stream()
                .map(CouponHistory::getId)
                .collect(Collectors.toList());
    }

    public List<CouponHistoryResponse> getReceivedCoupons(final Long receiverId) {
        return couponHistoryQueryRepository.findByReceiverId(receiverId)
                .stream()
                .map(CouponHistoryResponse::of)
                .collect(Collectors.toList());
    }
}
