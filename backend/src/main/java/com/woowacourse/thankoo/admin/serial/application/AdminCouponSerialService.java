package com.woowacourse.thankoo.admin.serial.application;

import com.woowacourse.thankoo.admin.serial.domain.AdminCouponSerialQueryRepository;
import com.woowacourse.thankoo.admin.serial.domain.CodeCreator;
import com.woowacourse.thankoo.admin.serial.domain.SerialCodes;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.admin.serial.application.dto.AdminCouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCouponSerialService {

    private final AdminCouponSerialQueryRepository couponSerialQueryRepository;
    private final CouponSerialRepository couponSerialRepository;
    private final MemberRepository memberRepository;
    private final CodeCreator codeCreator;

    public void save(final AdminCouponSerialRequest couponSerialRequest) {
        Member member = getMember(couponSerialRequest.getMemberId());
        SerialCodes serialCodes = SerialCodes.of(couponSerialRequest.getQuantity(), codeCreator);
        validateDuplicate(serialCodes);
        couponSerialRepository.saveAll(create(couponSerialRequest, serialCodes, member.getId()));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private void validateDuplicate(final SerialCodes serialCodes) {
        if (couponSerialQueryRepository.existsBySerialCodeValue(serialCodes.getSerialCodeValues())) {
            throw new InvalidCouponSerialException(ErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private List<CouponSerial> create(final AdminCouponSerialRequest couponSerialRequest,
                                             final SerialCodes serialCodes,
                                             final Long senderId) {
        List<SerialCode> values = serialCodes.getValues();
        return values.stream()
                .map(code -> couponSerialRequest.from(code, senderId))
                .collect(Collectors.toList());
    }
}
