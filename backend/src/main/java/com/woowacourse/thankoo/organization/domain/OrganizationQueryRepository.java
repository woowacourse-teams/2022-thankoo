package com.woowacourse.thankoo.organization.domain;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrganizationQueryRepository {

    private static final RowMapper<MemberOrganization> ROW_MAPPER = rowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<MemberOrganization> findMemberOrganizationsByMemberId(final Long memberId) {
        String sql = "SELECT om.organization_id AS o_id, o.id AS id, o.name AS name, o.code As code, "
                + "om.order_number AS order_number, om.last_accessed AS last_accessed "
                + "FROM organization_member AS om "
                + "JOIN organization AS o ON om.organization_id = o.id "
                + "WHERE om.member_id = (:memberId) "
                + "ORDER BY om.order_number";

        SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId);
        return jdbcTemplate.query(sql, parameter, ROW_MAPPER);
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

    public Optional<SimpleOrganization> findByCode(final String code) {
        String sql = "SELECT id, name FROM organization WHERE code = :code";

        SqlParameterSource parameter = new MapSqlParameterSource("code", code);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter,
                (rs, rowNum) -> new SimpleOrganization(rs.getLong("id"),
                        rs.getString("name")))
        ));
    }

    private static RowMapper<MemberOrganization> rowMapper() {
        return ((rs, rowNum) ->
                new MemberOrganization(rs.getLong("organization_id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("order_number"),
                        rs.getBoolean("last_accessed"))
        );
    }

    private static RowMapper<MemberModel> memberRowMapper() {
        return (rs, rowNum) -> new MemberModel(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("image_url"));
    }
}
