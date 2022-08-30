package com.woowacourse.thankoo.reservation.application.dto;

import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Email;
import com.woowacourse.thankoo.member.domain.Name;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReservationMessage {

    private static final String PRETEXT = "\uD83D\uDC9D 예약 요청이 도착했어요.";
    private static final String PRETEXT_RESPONSE = "\uD83D\uDC7B 예약 요청에 응답이 왔어요.";
    private static final String PRETEXT_CANCEL = "\uD83D\uDE05 예약이 취소되었어요ㅜ";
    private static final String TITLE_LINK = "https://thankoo.co.kr/reservations";
    private static final String SENDER = "요청자 : {0}";
    private static final String DATE = "예약 요청일 : {0}";
    private static final String RESERVATION_STATUS = "예약 상태 : {0}";
    private static final String COUPON = "쿠폰 : {0}";
    private static final Map<String, String> type = Map.of(
            "ACCEPT", "승인\uD83E\uDD70",
            "DENY", "거절\uD83D\uDE05");

    public static Message of(final Name sender,
                             final Email email,
                             final LocalDate date,
                             final CouponContent couponContent) {
        return Message.builder()
                .title(PRETEXT)
                .titleLink(TITLE_LINK)
                .email(List.of(email.getValue()))
                .content(MessageFormat.format(SENDER, sender.getValue()))
                .content(MessageFormat.format(DATE, date))
                .content(MessageFormat.format(COUPON, couponContent.getTitle()))
                .build();
    }

    public static Message updateOf(final Name sender, final Email email, final Reservation reservation) {
        return Message.builder()
                .title(PRETEXT_RESPONSE)
                .titleLink(TITLE_LINK)
                .email(List.of(email.getValue()))
                .content(MessageFormat.format(SENDER, sender.getValue()))
                .content(MessageFormat.format(COUPON, reservation.getCoupon().getCouponContent().getTitle()))
                .content(MessageFormat.format(RESERVATION_STATUS,
                        type.get(reservation.getReservationStatus().toString())))
                .build();
    }

    public static Message cancelOf(final Name sender, final Email email, final Reservation reservation) {
        return Message.builder()
                .email(List.of(email.getValue()))
                .title(PRETEXT_CANCEL)
                .titleLink(TITLE_LINK)
                .content(MessageFormat.format(SENDER, sender.getValue()))
                .content(MessageFormat.format(COUPON, reservation.getCoupon().getCouponContent().getTitle()))
                .build();
    }
}
