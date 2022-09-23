package com.woowacourse.thankoo.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public boolean existsById(final Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE id = :{id})";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        int count = jdbcTemplate.queryForObject(sql, sqlParameterSource, Integer.class);
        return count > 0;
    }
}
