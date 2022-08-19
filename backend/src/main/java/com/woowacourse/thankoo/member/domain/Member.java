package com.woowacourse.thankoo.member.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Email email;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "image_url", length = 2_000)
    private String imageUrl;

    public Member(final Long id, final String name, final String email, final String socialId, final String imageUrl) {
        this.id = id;
        this.name = new Name(name);
        this.email = new Email(email);
        this.socialId = socialId;
        this.imageUrl = imageUrl;
    }

    public Member(final String name, final String email, final String socialId, final String imageUrl) {
        this(null, name, email, socialId, imageUrl);
    }

    public void updateName(final String name) {
        this.name = new Name(name);
    }

    public void updateProfileImage(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean hasSameId(final List<Long> ids) {
        return ids.stream()
                .anyMatch(this::isSameId);
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", socialId='" + socialId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
