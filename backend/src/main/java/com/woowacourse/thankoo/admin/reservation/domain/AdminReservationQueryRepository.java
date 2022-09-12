package com.woowacourse.thankoo.admin.reservation.domain;

import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminReservationQueryRepository {

    private static final RowMapper<AdminReservationResponse> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<AdminReservationResponse> rowMapper() {
        return (rs, rowNum) ->
                new AdminReservationResponse(
                        rs.getLong("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTimestamp("time").toLocalDateTime(),
                        rs.getString("time_zone"),
                        rs.getString("status"),
                        rs.getLong("member_id"),
                        rs.getLong("coupon_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
    }

    public List<AdminReservationResponse> findAll(final LocalDate start, final LocalDate end, final String status) {
        if (start == null && end == null && status == null) {
            return findAll();
        }

        String sql = "SELECT * FROM reservation WHERE ";

        if (status == null) {
            sql += "created_at BETWEEN :start AND :end";
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("start", start)
                    .addValue("end", end);
            return jdbcTemplate.query(sql, parameters, rowMapper());
        }

        if (start == null && end == null) {
            sql += "status = :status";
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("status", status);
            return jdbcTemplate.query(sql, parameters, rowMapper());
        }

        sql += "created_at BETWEEN :start AND :end AND status = :status";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("start", start)
                .addValue("end", end)
                .addValue("status", status);
        return jdbcTemplate.query(sql, parameters, rowMapper());
    }

    public List<AdminReservationResponse> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }
}
