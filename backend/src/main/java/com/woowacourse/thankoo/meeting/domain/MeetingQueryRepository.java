package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.meeting.application.dto.MeetingQueryCondition;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MeetingQueryRepository {

    private static final RowMapper<MeetingCoupon> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<MeetingCoupon> rowMapper() {
        return (rs, rowNum) ->
                new MeetingCoupon(
                        rs.getLong("id"),
                        rs.getTimestamp("time").toLocalDateTime(),
                        TimeZoneType.of(rs.getString("time_zone")),
                        rs.getString("coupon_type"),
                        rs.getString("name"));
    }

    public List<MeetingCoupon> findMeetingsByMemberIdAndTimeAndStatus(
            final MeetingQueryCondition meetingQueryCondition) {
        String sql = "SELECT mt.id, mt.time, mt.time_zone, c.coupon_type, m.name "
                + "FROM meeting_member AS mm "
                + "JOIN meeting AS mt ON mm.meeting_id = mt.id "
                + "JOIN coupon AS c ON mt.coupon_id = c.id "
                + "JOIN member AS m ON c.sender_id = m.id OR c.receiver_id = m.id "
                + "WHERE mm.member_id = :memberId AND m.id != :memberId "
                + "AND mt.time > :time AND mt.status = :status";

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(meetingQueryCondition);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }
}
