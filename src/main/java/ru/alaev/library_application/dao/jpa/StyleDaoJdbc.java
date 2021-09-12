package ru.alaev.library_application.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.domain.Style;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StyleDaoJdbc implements StyleDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Long> getStyleIdByName(String style) {
        Map<String, Object> params = Collections.singletonMap("style", style);

        final List<Style> styles =
                jdbc.query("select * from style where st_name = :style", params, new StyleMapper());

        return styles.size() == 0 ? Optional.empty() : Optional.of(styles.get(0).getId());
    }

    private static class StyleMapper implements RowMapper<Style> {
        @Override
        public Style mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Style(rs.getLong("id_style"), rs.getString("st_name"));
        }
    }
}
