package ru.alaev.library_application.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.alaev.library_application.dao.StyleDao;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(StyleDaoJdbc.class)
class StyleDaoJdbcTest {
    public static final String NOVEL_NAME = "Novel";
    public static final long EXPECTED_ID_STYLE = 1L;
    public static final String WRONG_NAME_STYLE = "Novell";
    @Autowired
    StyleDao styleDao;

    @Test
    void shouldReturnStyleIdByName() {
        final Optional<Long> styleIdByName = styleDao.getStyleIdByName(NOVEL_NAME);

        assertThat(styleIdByName).isPresent();
        assertThat(styleIdByName.get()).isEqualTo(EXPECTED_ID_STYLE);
    }

    @Test
    void shouldReturnNullWhereStyleIdNotExist() {
        final Optional<Long> styleIdByName = styleDao.getStyleIdByName(WRONG_NAME_STYLE);

        assertThat(styleIdByName).isEmpty();
    }
}