package com.woowacourse.thankoo.member.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Members {

    private final List<Member> members;

    public Members(final List<Member> members) {
        this.members = List.copyOf(members);
    }

    public List<String> getEmails() {
        return members.stream()
                .map(member -> member.getEmail().getValue())
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }
}
