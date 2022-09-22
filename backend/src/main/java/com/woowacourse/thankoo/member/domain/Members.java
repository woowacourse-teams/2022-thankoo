package com.woowacourse.thankoo.member.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Members {

    private final List<Member> values;

    public Members(final List<Member> values) {
        this.values = List.copyOf(values);
    }

    public Members(final Set<Member> values) {
        this(new ArrayList<>(values));
    }

    public List<String> getEmails() {
        return values.stream()
                .map(member -> member.getEmail().getValue())
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
