package ru.alaev.library_application.dao.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.domain.Style;

@Repository
@RequiredArgsConstructor
public class StyleDaoJdbc implements StyleDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Long> getStyleIdByName(String styleName) {
        Map<String, Object> params = Collections.singletonMap("STYLE_NAME", styleName);

        final List<Style> styles =
            jdbc.query(
                "SELECT * "
                    + "FROM STYLES "
                    + "WHERE NAME = :STYLE_NAME",
                params,
                new StyleMapper());

        return styles.size() == 0 ? Optional.empty() : Optional.of(styles.get(0).getId());
    }

    @Override
    public List<Style> getAllStyles() {
        return jdbc.query(
            "SELECT * "
                + "FROM STYLES",
            new StyleMapper());
    }

    @Override
    public boolean isExistStyle(String styleName) {
        return getStyleIdByName(styleName).isPresent();
    }

    @Override
    public void save(Style style) {
        Map<String, Object> params = new HashMap<>();
        params.put("STYLE_NAME", style.getName());

        jdbc.update(
            "INSERT INTO STYLES (NAME) "
                + "VALUES (:STYLE_NAME)",
            params);
    }

    @Override
    public Optional<String> getStyleNameById(long styleId) {
        final List<Style> styles = jdbc.query(
            "SELECT * "
                + "FROM STYLES "
                + "WHERE id = :STYLE_ID",
            Map.of("STYLE_ID", styleId),
            new StyleMapper());

        return styles.size() == 0 ? Optional.empty() : Optional.of(styles.get(0).getName());
    }

    private static class StyleMapper implements RowMapper<Style> {

        @Override
        public Style mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Style(rs.getLong("id"), rs.getString("name"));
        }
    }
}
