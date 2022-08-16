package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.alarm.support.Alarm;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponMessage;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.domain.Members;
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

    @Alarm
    public void saveAll(final Long senderId, final CouponRequest couponRequest) {
        validateMember(senderId, couponRequest.getReceiverIds());
        Coupons coupons = new Coupons(couponRepository.saveAll(couponRequest.toEntities(senderId)));
        Members members = new Members(memberRepository.findByIdIn(coupons.getReceiverIds()));
        sendMessage(senderId, members.getEmails(), couponRequest.toCouponContent());
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

    private void sendMessage(final Long senderId, final List<String> emails, final CouponContent couponContent) {
        Member sender = getMember(senderId);
        AlarmManager.setResources(CouponMessage.create(sender.getName(), emails, couponContent));
    }

    private Member getMember(final Long senderId) {
        return memberRepository.findById(senderId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
