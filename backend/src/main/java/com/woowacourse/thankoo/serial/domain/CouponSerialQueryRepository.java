package com.woowacourse.thankoo.serial.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponSerialQueryRepository {

    private static final RowMapper<CouponSerialMember> ROW_MAPPER = rowMapper();

    private static RowMapper<CouponSerialMember> rowMapper() {
        return (rs, rowNum) ->
                new CouponSerialMember(
                        rs.getLong("id"),
                        rs.getString("code"),
                        rs.getLong("member_id"),
                        rs.getString("member_name"),
                        rs.getString("coupon_type"));
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Optional<CouponSerialMember> findByCode(final String code) {
        String sql = "SELECT s.id, s.code, m.id AS member_id, m.name AS member_name, s.coupon_type "
                + "FROM coupon_serial AS s "
                + "JOIN member AS m ON s.member_id = m.id "
                + "WHERE s.code = :code";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("code", code);

        return Optional
                .ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, sqlParameterSource, ROW_MAPPER)));
    }
}
