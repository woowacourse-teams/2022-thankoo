package com.woowacourse.thankoo.member.domain;

import com.woowacourse.thankoo.organization.domain.MemberModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public boolean existsById(final Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE id = :{id})";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        int count = jdbcTemplate.queryForObject(sql, sqlParameterSource, Integer.class);
        return count > 0;
    }

    public List<MemberModel> findOrganizationMembersExcludeMe(final Long organizationId, final Long memberId) {
        String sql = "SELECT m.id, m.name, m.email, m.image_url FROM organization AS o "
                + "LEFT JOIN organization_member AS om ON o.id = om.organization_id "
                + "LEFT JOIN member AS m ON om.member_id = m.id "
                + "WHERE o.id = :organizationId "
                + "AND m.id <> :memberId";

        SqlParameterSource parameter = new MapSqlParameterSource("organizationId", organizationId)
                .addValue("memberId", memberId);
        return jdbcTemplate.query(sql, parameter, memberRowMapper());
    }

    private static RowMapper<MemberModel> memberRowMapper() {
        return (rs, rowNum) -> new MemberModel(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("image_url"));
    }
}
