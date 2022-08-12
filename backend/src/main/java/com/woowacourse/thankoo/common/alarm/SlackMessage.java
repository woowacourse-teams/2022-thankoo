package com.woowacourse.thankoo.common.alarm;

import lombok.Getter;

@Getter
public enum SlackMessage {

    RECEIVE_COUPON("쿠폰을 받았어요! 확인해보러 갈까요? https://thankoo.co.kr/"),
    RECEIVE_RESERVATION("예약 요청이 왔어요. 확인해보러 갈까요? https://thankoo.co.kr/"),
    ACCEPT_RESERVATION("예약 요청에 성공했어요! 확인해보러 갈까요? https://thankoo.co.kr/"),
    DENY_RESERVATION("예약이 거절당했어요ㅜ 확인해보러 갈까요? https://thankoo.co.kr/"),
    MEETING("오늘은 미팅이 있는 날이에요! 확인해보러 갈까요? https://thankoo.co.kr/");

    private final String value;

    SlackMessage(final String value) {
        this.value = value;
    }
}
