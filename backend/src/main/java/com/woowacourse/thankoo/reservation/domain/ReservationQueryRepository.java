package com.woowacourse.thankoo.reservation.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<ReservationCoupon> rowMapper() {
        return (rs, rowNum) -> new ReservationCoupon(rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("coupon_type"),
                rs.getTimestamp("time").toLocalDateTime(),
                rs.getString("time_zone"));
    }

    @Deprecated
    public List<ReservationCoupon> findReceivedReservations(final Long senderId, final LocalDateTime dateTime) {
        String sql = "SELECT r.id as reservation_id, r.time, r.time_zone, "
                + "c.coupon_type, mr.name "
                + "FROM reservation as r "
                + "JOIN coupon as c ON r.coupon_id = c.id "
                + "JOIN member as ms ON c.sender_id = ms.id "
                + "JOIN member as mr ON c.receiver_id = mr.id "
                + "WHERE r.status = :status "
                + "AND r.time >= :dateTime "
                + "AND c.sender_id = :senderId "
                + "ORDER BY r.time ASC";

        SqlParameterSource parameters = new MapSqlParameterSource("senderId", senderId)
                .addValue("status", "WAITING")
                .addValue("dateTime", dateTime);
        return jdbcTemplate.query(sql, parameters, rowMapper());
    }

    public List<ReservationCoupon> findReceivedReservations(final Long senderId, final Long organizationId,
                                                            final LocalDateTime dateTime) {
        String sql = "SELECT r.id as reservation_id, r.time, r.time_zone, "
                + "c.coupon_type, mr.name "
                + "FROM reservation as r "
                + "JOIN coupon as c ON r.coupon_id = c.id "
                + "JOIN member as ms ON c.sender_id = ms.id "
                + "JOIN member as mr ON c.receiver_id = mr.id "
                + "WHERE r.status = :status "
                + "AND r.time >= :dateTime "
                + "AND c.sender_id = :senderId "
                + "AND c.organization_id = :organizationId "
                + "ORDER BY r.time ASC";

        SqlParameterSource parameters = new MapSqlParameterSource("senderId", senderId)
                .addValue("status", "WAITING")
                .addValue("dateTime", dateTime)
                .addValue("organizationId", organizationId);

        return jdbcTemplate.query(sql, parameters, rowMapper());
    }

    public List<ReservationCoupon> findSentReservations(final Long receiverId, final LocalDateTime dateTime) {
        String sql = "SELECT r.id as reservation_id, r.time, r.time_zone, "
                + "c.coupon_type, ms.name "
                + "FROM reservation as r "
                + "JOIN coupon as c ON r.coupon_id = c.id "
                + "JOIN member as ms ON c.sender_id = ms.id "
                + "JOIN member as mr ON c.receiver_id = mr.id "
                + "WHERE r.status = :status "
                + "AND r.time >= :dateTime "
                + "AND c.receiver_id = :receiverId "
                + "ORDER BY r.time ASC";

        SqlParameterSource parameters = new MapSqlParameterSource("receiverId", receiverId)
                .addValue("status", "WAITING")
                .addValue("dateTime", dateTime);
        return jdbcTemplate.query(sql, parameters, rowMapper());
    }
}
