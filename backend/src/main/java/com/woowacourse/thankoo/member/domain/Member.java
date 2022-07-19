package com.woowacourse.thankoo.member.domain;

import com.woowacourse.thankoo.common.domain.BaseEntity;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.Objects;
import java.util.regex.Pattern;
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

    private static final int NAME_MAX_LENGTH = 20;
    private static final String EMAIL_REGEX_PATTERN = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Column(name = "email")
    private String email;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "image_url", length = 2_000)
    private String imageUrl;

    public Member(final Long id, final String name, final String email, final String socialId, final String imageUrl) {
        validateEmail(email);
        this.id = id;
        this.name = new Name(name);
        this.email = email;
        this.socialId = socialId;
        this.imageUrl = imageUrl;
    }

    public Member(final String name, final String email, final String socialId, final String imageUrl) {
        this(null, name, email, socialId, imageUrl);
    }

    public void updateName(final String name) {
        this.name = new Name(name);
    }

    private void validateEmail(final String email) {
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX_PATTERN, email)) {
            throw new InvalidMemberException(ErrorType.INVALID_MEMBER_EMAIL);
        }
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
