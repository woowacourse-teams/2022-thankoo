package com.woowacourse.thankoo.coupon.infrastructure.integrate;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.MeetingProvider;
import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.MeetingResponse;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaMeetingProvider implements MeetingProvider {

    private final MeetingRepository meetingRepository;

    @Override
    public MeetingResponse getMeetingByCouponId(final Long couponId) {
        return MeetingResponse.of(
                meetingRepository.findTopByCouponId(couponId)
                        .orElseThrow(() -> new InvalidMeetingException(ErrorType.NOT_FOUND_MEETING)));
    }
}
