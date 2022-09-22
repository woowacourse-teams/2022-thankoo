package com.woowacourse.thankoo.admin.coupon.domain;

import com.woowacourse.thankoo.admin.coupon.domain.dto.AdminCouponSearchCondition;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminCouponQueryRepository {

    private static final RowMapper<AdminCoupon> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<AdminCoupon> rowMapper() {
        return (rs, rowNum) ->
                new AdminCoupon(rs.getLong("coupon_id"),
                        new Member(rs.getLong("sender_id"), rs.getString("sender_name"),
                                rs.getString("sender_email"), rs.getString("sender_social_id"),
                                rs.getString("sender_image_url")),
                        new Member(rs.getLong("receiver_id"), rs.getString("receiver_name"),
                                rs.getString("receiver_email"), rs.getString("receiver_social_id"),
                                rs.getString("receiver_image_url")),
                        rs.getString("coupon_type"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
    }

    public List<AdminCoupon> findAllByStatusAndDate(final AdminCouponSearchCondition couponSearchCondition) {
        String sql = "SELECT c.id as coupon_id, "
                + "s.id as sender_id, s.name as sender_name, "
                + "s.email as sender_email, s.social_id as sender_social_id, "
                + "s.image_url as sender_image_url, "
                + "r.id as receiver_id, r.name as receiver_name, "
                + "r.email as receiver_email, r.social_id as receiver_social_id, "
                + "r.image_url as receiver_image_url, "
                + "c.coupon_type, c.status, c.created_at, c.modified_at "
                + "FROM coupon as c "
                + "JOIN member as s ON c.sender_id = s.id "
                + "JOIN member as r ON c.receiver_id = r.id "
                + "WHERE c.status IN (:statuses) "
                + "AND DATE(c.created_at) BETWEEN :startDate AND :endDate "
                + "ORDER BY c.created_at DESC ";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(couponSearchCondition);
        return jdbcTemplate.query(sql, sqlParameterSource, ROW_MAPPER);
    }
}
