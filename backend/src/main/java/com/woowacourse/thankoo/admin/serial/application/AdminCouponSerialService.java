package com.woowacourse.thankoo.admin.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import com.woowacourse.thankoo.serial.domain.SerialCodeCreator;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCouponSerialService {

    private final CouponSerialRepository couponSerialRepository;
    private final MemberRepository memberRepository;

    public void save(final CouponSerialRequest couponSerialRequest) {
        Member coach = getMember(couponSerialRequest.getMemberId());
        List<CouponSerial> couponSerials = new ArrayList<>();
        for (int i = 0; i < couponSerialRequest.getQuantity(); i++) {
            CouponSerial couponSerial = new CouponSerial(
                    new SerialCodeCreator(),
                    coach.getId(),
                    CouponSerialType.of(couponSerialRequest.getCouponType()));
            validateCode(couponSerial.getCode());
            couponSerials.add(couponSerial);
        }
        couponSerialRepository.saveAll(couponSerials);
    }

    private void validateCode(final String code) {
        if (couponSerialRepository.existsByCode(code)) {
            throw new InvalidCouponSerialException(ErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
