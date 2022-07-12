package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponHistoryQueryRepository {

    private static final RowMapper<MemberCouponHistory> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<MemberCouponHistory> rowMapper() {
        return (rs, rowNum) ->
                new MemberCouponHistory(rs.getLong("coupon_history_id"),
                        new Member(rs.getLong("sender_id"), rs.getString("sender_name"),
                                rs.getString("email"), rs.getString("social_id"),
                                rs.getString("image_url")),
                        new Member(rs.getLong("receiver_id"), rs.getString("receiver_name"),
                                rs.getString("email"), rs.getString("social_id"),
                                rs.getString("image_url")),
                        rs.getString("coupon_type"),
                        rs.getString("title"),
                        rs.getString("message"));
    }

    public List<MemberCouponHistory> findByReceiverId(final Long receiverId) {
        String sql = "SELECT c.id as coupon_history_id, "
                + "s.id as sender_id, s.name as sender_name, "
                + "s.email as sender_email, s.social_id as sender_social_id,"
                + "s.image_url as sender_image_url,"
                + "r.id as receiver_id, r.name as receiver_name, "
                + "r.email as receiver_email, r.social_id as receiver_social_id,"
                + "r.image_url as receiver_image_url,"
                + "c.coupon_type, c.title, c.message "
                + "FROM coupon_history as c "
                + "JOIN member as s ON c.sender_id = s.id "
                + "JOIN member as r ON c.receiver_id = r.id "
                + "WHERE c.receiver_id = :receiverId "
                + "ORDER BY c.id DESC";

        return jdbcTemplate.query(sql, new MapSqlParameterSource("receiverId", receiverId), ROW_MAPPER);
    }
}
