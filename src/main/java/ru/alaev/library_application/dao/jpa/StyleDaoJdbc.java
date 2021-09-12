package ru.alaev.library_application.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.StyleDao;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StyleDaoJdbc implements StyleDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Long> getStyleIdByName(String style) {
        Map<String, Object> params = Collections.singletonMap("style", style);
        final Long id_style =
                jdbc.queryForObject("select id_style from style where st_name = :style", params, Long.class);

        return Optional.ofNullable(id_style);
    }
}
