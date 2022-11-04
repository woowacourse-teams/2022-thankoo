package com.woowacourse.thankoo.alarm.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Emails {

    private final List<Email> values;

    public Emails(final List<Email> values) {
        this.values = values;
    }

    public static Emails from(final List<String> emails) {
        return new Emails(emails.stream()
                .map(Email::new)
                .collect(Collectors.toList()));
    }

    public List<String> getEmails() {
        return values.stream()
                .map(Email::getValue)
                .collect(Collectors.toList());
    }
}
