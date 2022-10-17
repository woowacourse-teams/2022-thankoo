package com.woowacourse.thankoo.organization.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberModel {

    private Long id;
    private String name;
    private String email;
    private String imageUrl;

    public MemberModel(final Long id, final String name, final String email, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberModel)) {
            return false;
        }
        MemberModel that = (MemberModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(email, that.email) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, imageUrl);
    }

    @Override
    public String toString() {
        return "MemberModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
