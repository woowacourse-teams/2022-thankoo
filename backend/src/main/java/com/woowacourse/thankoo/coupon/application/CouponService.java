package com.woowacourse.thankoo.coupon.application;

import com.woowacourse.thankoo.alarm.support.Alarm;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.support.AlarmMessageRequest;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
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

    @Alarm
    public void saveAll(final Long senderId, final CouponRequest couponRequest) {
        validateMember(senderId, couponRequest.getReceiverIds());
        Coupons coupons = new Coupons(couponRepository.saveAll(couponRequest.toEntities(senderId)));
        List<Member> values = memberRepository.findByIdIn(coupons.getReceiverIds());
        Member sender = getMember(senderId);

        // todo : 리팩토링
        List<AlarmMessageRequest> request = new ArrayList<>();
        for (Member member : values) {
            for (Coupon coupon : coupons.getValues()) {
                CouponContent couponContent = coupon.getCouponContent();
                String senderName = "보내는 이 : " + sender.getName().getValue();
                String title = "제목 : " + couponContent.getTitle();
                String type = "타입 : " + couponContent.getCouponType().getValue();
                request.add(new AlarmMessageRequest(member.getEmail().getValue(), "커피 쿠폰이 도착했어요",
                        List.of(senderName, title, type)));
            }
        }

        AlarmManager.setResources(request);
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

    private Member getMember(final Long senderId) {
        return memberRepository.findById(senderId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private void sendMessage(final List<Member> values) {

    }
}
