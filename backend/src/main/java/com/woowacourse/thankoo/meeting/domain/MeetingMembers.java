package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
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

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.PERSIST, orphanRemoval = true)
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

    public boolean have(final Member member) {
        return meetingMembers.stream()
                .anyMatch(meetingMember -> meetingMember.has(member));
    }
}
