package ru.alaev.library_application.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.domain.Style;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class StyleDaoJdbc implements StyleDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Long> getStyleIdByName(String styleName) {
        Map<String, Object> params = Collections.singletonMap("style", styleName);

        final List<Style> styles =
                jdbc.query("select * from style where st_name = :style", params, new StyleMapper());

        return styles.size() == 0 ? Optional.empty() : Optional.of(styles.get(0).getId());
    }

    @Override
    public List<Style> getAllStyles() {
        return jdbc.query("select * from style", new StyleMapper());
    }

    @Override
    public boolean isExistStyle(String styleName) {
        return getStyleIdByName(styleName).isPresent();
    }

    @Override
    public void save(Style style) {
        Map<String, Object> params = new HashMap<>();
        params.put("st_name", style.getName());

        jdbc.update("insert into style (st_name) values(:st_name)", params);
    }

    @Override
    public Optional<String> getStyleNameById(long idStyle) {
        final List<Style> styles = jdbc.query("select * from style where id_style = :idStyle",
                                              Map.of("idStyle", idStyle),
                                              new StyleMapper());

        return styles.size() == 0 ? Optional.empty() : Optional.of(styles.get(0).getName());
    }

    private static class StyleMapper implements RowMapper<Style> {
        @Override
        public Style mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Style(rs.getLong("id_style"), rs.getString("st_name"));
        }
    }
}
