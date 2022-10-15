package com.woowacourse.thankoo.admin.organization.application.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminGetOrganizationsRequest {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public AdminGetOrganizationsRequest(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "AdminGetOrganizationsRequest{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
