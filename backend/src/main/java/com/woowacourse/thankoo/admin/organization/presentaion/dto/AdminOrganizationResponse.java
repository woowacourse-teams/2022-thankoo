package com.woowacourse.thankoo.admin.organization.presentaion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationCode;
import com.woowacourse.thankoo.organization.domain.OrganizationName;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminOrganizationResponse {

    private Long organizationId;
    private String name;
    private String code;
    private int limitedSize;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private AdminOrganizationResponse(final Long organizationId,
                                      final String name,
                                      final String code,
                                      final int limitedSize,
                                      final LocalDateTime createdAt,
                                      final LocalDateTime modifiedAt) {
        this.organizationId = organizationId;
        this.name = name;
        this.code = code;
        this.limitedSize = limitedSize;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AdminOrganizationResponse from(final Organization organization) {
        OrganizationName organizationName = organization.getName();
        OrganizationCode organizationCode = organization.getCode();
        return new AdminOrganizationResponse(organization.getId(),
                organizationName.getValue(),
                organizationCode.getValue(),
                organization.getLimitedSize(),
                organization.getCreatedAt(),
                organization.getModifiedAt());
    }

    @Override
    public String toString() {
        return "AdminGetOrganizationResponse{" +
                "organizationId=" + organizationId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", limitedSize=" + limitedSize +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
