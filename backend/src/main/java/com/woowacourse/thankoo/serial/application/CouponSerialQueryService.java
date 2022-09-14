package com.woowacourse.thankoo.serial.application;

import com.woowacourse.thankoo.serial.domain.CouponSerialQueryRepository;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponSerialQueryService {

    private final CouponSerialQueryRepository couponSerialQueryRepository;

    public CouponSerialResponse getByCode(final String code) {
        return CouponSerialResponse.from(couponSerialQueryRepository.findByCode(code));
    }
}
