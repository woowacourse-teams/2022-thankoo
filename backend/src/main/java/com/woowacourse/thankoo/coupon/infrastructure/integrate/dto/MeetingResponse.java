package com.woowacourse.thankoo.coupon.infrastructure.integrate.dto;

import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MeetingResponse {

    private Long meetingId;
    private List<MemberResponse> members;
    private TimeResponse time;
    private String status;

    private MeetingResponse(final Long meetingId,
                            final List<MemberResponse> members,
                            final TimeResponse time,
                            final String status) {
        this.meetingId = meetingId;
        this.members = members;
        this.time = time;
        this.status = status.toLowerCase(Locale.ROOT);
    }

    public static MeetingResponse of(final Meeting meeting) {
        List<MemberResponse> memberResponses = toMemberResponses(
                meeting);
        return new MeetingResponse(meeting.getId(),
                memberResponses,
                TimeResponse.of(meeting.getTimeUnit()),
                meeting.getMeetingStatus().name());
    }

    private static List<MemberResponse> toMemberResponses(final Meeting meeting) {
        return meeting.getMeetingMembers().getMeetingMembers()
                .stream()
                .map(meetingMember -> MemberResponse.of(meetingMember.getMember()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "MeetingResponse{" +
                "meetingId=" + meetingId +
                ", members=" + members +
                ", time=" + time +
                ", status='" + status + '\'' +
                '}';
    }
}
