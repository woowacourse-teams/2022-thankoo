package com.woowacourse.thankoo.admin.coupon.infrastructure;

import com.woowacourse.thankoo.admin.meeting.domain.AdminMeetingRepository;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class AdminCouponMeetingService implements AdminMeetingProvider {

    private final AdminMeetingRepository adminMeetingRepository;

    @Override
    public void finishMeetings(final List<Coupon> coupons) {
        adminMeetingRepository.updateMeetingStatus(MeetingStatus.ON_PROGRESS, MeetingStatus.FINISHED, coupons);
    }
}
