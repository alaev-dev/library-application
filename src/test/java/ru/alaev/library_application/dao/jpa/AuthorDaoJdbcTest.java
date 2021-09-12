package ru.alaev.library_application.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    public static final long EXPECTED_ID_PUSHKIN = 1L;
    public static final String WRONG_NAME_AUTHOR = "Pushkinn";
    private static final String AUTHOR_NAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Orwell";
    private static final int EXPECTED_COUNT_AUTHORS = 2;
    @Autowired
    AuthorDao authorDao;

    @Test
    void shouldReturnAllAuthors() {
        List<Author> allAuthors = authorDao.getAllAuthors();

        assertThat(allAuthors.stream()
                .map(Author::getName))
                .hasSize(EXPECTED_COUNT_AUTHORS)
                .contains(AUTHOR_NAME_1, AUTHOR_NAME_2);
    }

    @Test
    void shouldReturnAuthorIdByName() {
        final Optional<Long> authorIdByName = authorDao.getAuthorIdByName(AUTHOR_NAME_1);

        assertThat(authorIdByName).isPresent();
        assertThat(authorIdByName.get()).isEqualTo(EXPECTED_ID_PUSHKIN);
    }

    @Test
    void shouldReturnNullWhenAuthorNotExist() {
        final Optional<Long> authorIdByName = authorDao.getAuthorIdByName(WRONG_NAME_AUTHOR);

        assertThat(authorIdByName).isEmpty();
    }
}