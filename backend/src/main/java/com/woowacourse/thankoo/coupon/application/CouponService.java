package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponSerialCreator;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
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
    private final CouponSerialCreator couponSerialCreator;

    public void saveAll(final Long senderId, final CouponRequest couponRequest) {
        validateMember(senderId, couponRequest.getReceiverIds());
        Coupons coupons = Coupons.distribute(couponRequest.toEntities(senderId));
        couponRepository.saveAll(coupons.getValues());
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

    public Long saveWithSerialCode(final Long memberId, final CouponSerialRequest couponSerialRequest) {
        Coupon coupon = couponRepository.save(couponSerialCreator.create(memberId, couponSerialRequest.getSerialCode()));
        return coupon.getId();
    }
}
