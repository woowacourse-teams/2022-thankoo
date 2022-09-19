package com.woowacourse.thankoo.admin.serial.domain;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminCouponSerialQueryRepository {

    private static final RowMapper<CouponSerialMember> ROW_MAPPER = rowMapper();

    private static RowMapper<CouponSerialMember> rowMapper() {
        return (rs, rowNum) ->
                new CouponSerialMember(
                        rs.getLong("id"),
                        rs.getString("code"),
                        rs.getLong("sender_id"),
                        rs.getString("sender_name"),
                        rs.getString("coupon_type"),
                        rs.getString("status"));
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<CouponSerialMember> findByMemberId(final Long memberId) {
        String sql = "SELECT s.id, s.code, m.id AS sender_id, m.name AS sender_name, s.coupon_type, s.status "
                + "FROM coupon_serial AS s "
                + "JOIN member AS m ON s.sender_id = m.id "
                + "WHERE s.sender_id = :memberId";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return jdbcTemplate.query(sql, sqlParameterSource, ROW_MAPPER);
    }
}
