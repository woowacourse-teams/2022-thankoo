package com.woowacourse.thankoo.member.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
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

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "image_url", length = 2_000)
    private String imageUrl;

    public Member(final Long id, final String name, final String email, final String socialId, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.socialId = socialId;
        this.imageUrl = imageUrl;
    }

    public Member(final String nickname, final String email, final String socialId, final String imageUrl) {
        this(null, nickname, email, socialId, imageUrl);
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
