package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeartQueryRepository {

    private static final RowMapper<MemberHeart> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<MemberHeart> rowMapper() {
        return (rs, rowNum) -> new MemberHeart(rs.getLong("heart_id"),
                new Member(rs.getLong("sender_id"), rs.getString("sender_name"),
                        rs.getString("sender_email"), rs.getString("sender_social_id"),
                        rs.getString("sender_image_url")),
                rs.getInt("count"),
                rs.getTimestamp("modified_at").toLocalDateTime());
    }

    public List<MemberHeart> findByReceiverIdAndIsFinal(final Long receiverId, final boolean isFinal) {
        String sql = "SELECT h.id AS heart_id, "
                + "m.id AS sender_id, m.name AS sender_name, "
                + "m.email AS sender_email, m.social_id AS sender_social_id, "
                + "m.image_url AS sender_image_url, "
                + "h.count, h.modified_at  "
                + "FROM heart AS h "
                + "JOIN member AS m ON h.sender_id = m.id "
                + "WHERE h.receiver_id = (:receiverId) "
                + "AND h.is_final = (:isFinal) "
                + "ORDER BY h.modified_at DESC";

        SqlParameterSource parameters = new MapSqlParameterSource("receiverId", receiverId)
                .addValue("isFinal", isFinal);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }
}
