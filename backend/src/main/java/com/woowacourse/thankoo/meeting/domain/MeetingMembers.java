package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMembers {

    private static final int STANDARD_MEMBER_COUNT = 2;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST)
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    public MeetingMembers(final List<MeetingMember> meetingMembers) {
        validateMemberCount(meetingMembers);
        this.meetingMembers.addAll(meetingMembers);
    }

    private void validateMemberCount(final List<MeetingMember> meetingMembers) {
        if (meetingMembers.size() != STANDARD_MEMBER_COUNT) {
            throw new InvalidMeetingException(ErrorType.INVALID_MEETING_MEMBER_COUNT);
        }
    }

    public boolean notContainsExactly(final Long receiverId, final Long senderId) {
        return meetingMembers.stream()
                .anyMatch(meetingMember -> !hasMember(receiverId, senderId, meetingMember));
    }

    private boolean hasMember(final Long receiverId, final Long senderId, final MeetingMember meetingMember) {
        return meetingMember.isSameMemberId(receiverId) || meetingMember.isSameMemberId(senderId);
    }

}
