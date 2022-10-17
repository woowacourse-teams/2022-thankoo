package com.woowacourse.thankoo.admin.serial.domain;

import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminCouponSerialQueryRepository {

    private static final RowMapper<CouponSerialMember> ROW_MAPPER = rowMapper();

    private static RowMapper<CouponSerialMember> rowMapper() {
        return (rs, rowNum) ->
                new CouponSerialMember(
                        rs.getLong("id"),
                        rs.getLong("organization_id"),
                        rs.getString("code"),
                        rs.getLong("sender_id"),
                        rs.getString("sender_name"),
                        rs.getString("coupon_type"),
                        rs.getString("status"));
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<CouponSerialMember> findByMemberId(final Long memberId) {
        String sql =
                "SELECT s.id, o.id AS organization_id, s.code, m.id AS sender_id, m.name AS sender_name, s.coupon_type, s.status "
                        + "FROM coupon_serial AS s "
                        + "JOIN member AS m ON s.sender_id = m.id "
                        + "JOIN organization_member AS am ON m.id = am.id "
                        + "JOIN organization AS o ON am.organization_id = o.id "
                        + "WHERE s.sender_id = :memberId";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return jdbcTemplate.query(sql, sqlParameterSource, ROW_MAPPER);
    }

    public boolean existsBySerialCodeValue(final List<String> serialCodes) {
        String sql = "SELECT COUNT(*) FROM coupon_serial AS c WHERE c.code IN (:serialCodes) LIMIT 1";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("serialCodes", serialCodes);

        int count = jdbcTemplate.queryForObject(sql, sqlParameterSource, Integer.class);
        return count > 0;
    }
}
