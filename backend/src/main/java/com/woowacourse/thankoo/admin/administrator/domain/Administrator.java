package com.woowacourse.thankoo.admin.administrator.domain;

import com.woowacourse.thankoo.admin.common.domain.AdminBaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrator")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Administrator extends AdminBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AdministratorRole role;

    public Administrator(final Long id, final String name, final String password,
                         final AdministratorRole role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Administrator(final String name, final String password,
                         final AdministratorRole role) {
        this(null, name, password, role);
    }

    public boolean isSamePassword(final String password) {
        return this.password.equals(password);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Administrator)) {
            return false;
        }
        Administrator that = (Administrator) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
