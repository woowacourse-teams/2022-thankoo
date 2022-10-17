package com.woowacourse.thankoo.organization.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class SimpleOrganization {

    private final Long id;
    private final String name;

    public SimpleOrganization(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleOrganization)) {
            return false;
        }
        SimpleOrganization that = (SimpleOrganization) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "SimpleOrganization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
