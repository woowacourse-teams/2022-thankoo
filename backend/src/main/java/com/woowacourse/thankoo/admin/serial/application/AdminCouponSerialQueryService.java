package com.woowacourse.thankoo.admin.serial.application;

import com.woowacourse.thankoo.admin.serial.domain.AdminCouponSerialQueryRepository;
import com.woowacourse.thankoo.admin.serial.presentation.dto.AdminCouponSerialResponse;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCouponSerialQueryService {

    private final AdminCouponSerialQueryRepository couponSerialQueryRepository;

    public List<AdminCouponSerialResponse> getByMemberId(final Long memberId) {
        List<CouponSerialMember> couponSerialMembers = couponSerialQueryRepository.findByMemberId(memberId);
        return couponSerialMembers.stream()
                .map(AdminCouponSerialQueryService::toCouponSerialResponse)
                .collect(Collectors.toList());
    }

    private static AdminCouponSerialResponse toCouponSerialResponse(final CouponSerialMember couponSerial) {
        return new AdminCouponSerialResponse(couponSerial.getId(),
                couponSerial.getCode().getValue(),
                couponSerial.getSenderId(),
                couponSerial.getSenderName(),
                couponSerial.getCouponType().getValue());
    }
}
