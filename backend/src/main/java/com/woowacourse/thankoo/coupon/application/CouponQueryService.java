package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.coupon.domain.CouponQueryRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatusGroup;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
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
