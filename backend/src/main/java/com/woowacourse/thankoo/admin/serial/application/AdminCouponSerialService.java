package com.woowacourse.thankoo.admin.serial.application;

import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import com.woowacourse.thankoo.serial.domain.SerialCodes;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import com.woowacourse.thankoo.serial.infrastructure.SerialCodeCreator;
import java.util.List;
import java.util.stream.Collectors;
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
        SerialCodes serialCodes = SerialCodes.of(couponSerialRequest.getQuantity(), new SerialCodeCreator());
        validateDuplicate(serialCodes);
        List<CouponSerial> couponSerials = createCouponSerials(coach, serialCodes, couponSerialRequest.getCouponType());
        couponSerialRepository.saveAll(couponSerials);
    }

    private void validateDuplicate(final SerialCodes serialCodes) {
        if (couponSerialRepository.existsBySerialCodeValue(getCodes(serialCodes))) {
            throw new InvalidCouponSerialException(ErrorType.DUPLICATE_COUPON_SERIAL);
        }
    }

    private static List<String> getCodes(final SerialCodes serialCodes) {
        return serialCodes.getValues().stream()
                .map(SerialCode::getValue)
                .collect(Collectors.toList());
    }

    private List<CouponSerial> createCouponSerials(final Member coach,
                                                   final SerialCodes serialCodes,
                                                   final String couponType) {
        List<SerialCode> values = serialCodes.getValues();
        return values.stream()
                .map(code -> new CouponSerial(code, coach.getId(), CouponSerialType.of(couponType), NOT_USED))
                .collect(Collectors.toList());
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
