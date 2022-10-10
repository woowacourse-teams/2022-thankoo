package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponQueryRepository {

    private static final RowMapper<MemberCoupon> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<MemberCoupon> rowMapper() {
        return (rs, rowNum) ->
                new MemberCoupon(rs.getLong("coupon_id"),
                        new Member(rs.getLong("sender_id"), rs.getString("sender_name"),
                                rs.getString("sender_email"), rs.getString("sender_social_id"),
                                rs.getString("sender_image_url")),
                        new Member(rs.getLong("receiver_id"), rs.getString("receiver_name"),
                                rs.getString("receiver_email"), rs.getString("receiver_social_id"),
                                rs.getString("receiver_image_url")),
                        rs.getString("coupon_type"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getString("status"),
                        rs.getDate("created_at").toLocalDate());
    }

    @Deprecated(since = "when organization will be merged")
    public List<MemberCoupon> findByReceiverIdAndStatus(final Long receiverId,
                                                        final List<String> couponStatuses) {
        String sql = "SELECT c.id as coupon_id, "
                + "s.id as sender_id, s.name as sender_name, "
                + "s.email as sender_email, s.social_id as sender_social_id, "
                + "s.image_url as sender_image_url,"
                + "r.id as receiver_id, r.name as receiver_name, "
                + "r.email as receiver_email, r.social_id as receiver_social_id,"
                + "r.image_url as receiver_image_url,"
                + "c.coupon_type, c.title, c.message, c.status, c.created_at "
                + "FROM coupon as c "
                + "JOIN member as s ON c.sender_id = s.id "
                + "JOIN member as r ON c.receiver_id = r.id "
                + "WHERE c.receiver_id = :receiverId "
                + "AND c.status IN (:couponStatuses) "
                + "ORDER BY c.id DESC";

        SqlParameterSource parameters = new MapSqlParameterSource("receiverId", receiverId)
                .addValue("couponStatuses", couponStatuses);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }

    public List<MemberCoupon> findByOrganizationIdAndReceiverIdAndStatus(final Long organizationId,
                                                                         final Long receiverId,
                                                                         final List<String> couponStatuses) {
        String sql = "SELECT c.id as coupon_id, "
                + "s.id as sender_id, s.name as sender_name, "
                + "s.email as sender_email, s.social_id as sender_social_id, "
                + "s.image_url as sender_image_url,"
                + "r.id as receiver_id, r.name as receiver_name, "
                + "r.email as receiver_email, r.social_id as receiver_social_id,"
                + "r.image_url as receiver_image_url,"
                + "c.coupon_type, c.title, c.message, c.status, c.created_at "
                + "FROM coupon as c "
                + "JOIN member as s ON c.sender_id = s.id "
                + "JOIN member as r ON c.receiver_id = r.id "
                + "WHERE c.organization_id = :organizationId "
                + "AND c.receiver_id = :receiverId "
                + "AND c.status IN (:couponStatuses) "
                + "ORDER BY c.id DESC";

        SqlParameterSource parameters = new MapSqlParameterSource("organizationId", organizationId)
                .addValue("receiverId", receiverId)
                .addValue("couponStatuses", couponStatuses);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }

    public List<MemberCoupon> findBySenderId(final Long senderId) {
        String sql = "SELECT c.id as coupon_id, "
                + "s.id as sender_id, s.name as sender_name, "
                + "s.email as sender_email, s.social_id as sender_social_id,"
                + "s.image_url as sender_image_url,"
                + "r.id as receiver_id, r.name as receiver_name, "
                + "r.email as receiver_email, r.social_id as receiver_social_id,"
                + "r.image_url as receiver_image_url,"
                + "c.coupon_type, c.title, c.message, c.status, c.created_at "
                + "FROM coupon as c "
                + "JOIN member as s ON c.sender_id = s.id "
                + "JOIN member as r ON c.receiver_id = r.id "
                + "WHERE c.sender_id = :senderId "
                + "ORDER BY c.id DESC";

        SqlParameterSource parameters = new MapSqlParameterSource("senderId", senderId);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }

    public Optional<MemberCoupon> findByCouponId(final Long couponId) {
        String sql = "SELECT c.id as coupon_id, "
                + "s.id as sender_id, s.name as sender_name, "
                + "s.email as sender_email, s.social_id as sender_social_id,"
                + "s.image_url as sender_image_url,"
                + "r.id as receiver_id, r.name as receiver_name, "
                + "r.email as receiver_email, r.social_id as receiver_social_id,"
                + "r.image_url as receiver_image_url,"
                + "c.coupon_type, c.title, c.message, c.status, c.created_at "
                + "FROM coupon as c "
                + "JOIN member as s ON c.sender_id = s.id "
                + "JOIN member as r ON c.receiver_id = r.id "
                + "WHERE c.id = :couponId ";

        SqlParameterSource parameters = new MapSqlParameterSource("couponId", couponId);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, ROW_MAPPER));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public CouponTotal getCouponCount(final Long memberId) {
        String sql = "SELECT COUNT(CASE WHEN sender_id = :memberId THEN 1 END) AS sent_count, "
                + "COUNT(CASE WHEN receiver_id = :memberId THEN 1 END) AS received_count "
                + "FROM coupon "
                + "WHERE sender_id = :memberId "
                + "OR receiver_id = :memberId";

        SqlParameterSource parameters = new MapSqlParameterSource("memberId", memberId);
        return jdbcTemplate.queryForObject(sql,
                parameters,
                (rs, rowNum) -> new CouponTotal(rs.getInt("sent_count"), rs.getInt("received_count")));
    }
}
