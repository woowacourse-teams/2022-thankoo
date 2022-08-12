package com.woowacourse.thankoo.alarm;

import lombok.Getter;

@Getter
public enum AlarmMessage {

    RECEIVE_COUPON("쿠폰을 받았어요! 확인해보러 갈까요? https://thankoo.co.kr/"),
    RECEIVE_RESERVATION("예약 요청이 왔어요. 확인해보러 갈까요? https://thankoo.co.kr/"),
    CANCEL_RESERVATION("예약이 거절되었습니다. 확인해보러 갈까요? https://thankoo.co.kr/"),
    RESPONSE_RESERVATION("예약 요청에 응답이 왔어요! 확인해보러 갈까요? https://thankoo.co.kr/"),
    MEETING("오늘은 미팅이 있는 날이에요! 확인해보러 갈까요? https://thankoo.co.kr/");

    private final String value;

    AlarmMessage(final String value) {
        this.value = value;
    }
}
