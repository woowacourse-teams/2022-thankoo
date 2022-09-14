package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponSerialService {

    private final CouponSerialRepository couponSerialRepository;
    private final MemberRepository memberRepository;

    public Long save(final CouponSerialRequest couponSerialRequest) {
        Member coach = getMember(couponSerialRequest.getCoachName());
        CouponSerial couponSerial = couponSerialRequest.toEntity(coach.getId());
        return couponSerialRepository.save(couponSerial).getId();
    }

    private Member getMember(final String name) {
        return memberRepository.findByNameValue(name)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
