package com.woowacourse.thankoo.coupon.infrastructure;

import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.MeetingResponse;

public interface MeetingProvider {

    MeetingResponse getOnProgressMeeting(Long couponId);
}
