package ru.alaev.library_application.dao.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.domain.Author;

@JdbcTest
@ComponentScan("ru.alaev.library_application.dao.jdbc")
class AuthorDaoJdbcTest {

    public static final long EXPECTED_ID_PUSHKIN = 1L;
    public static final String WRONG_NAME_AUTHOR = "Pushkinn";
    public static final int EXPECTED_COUNT_AUTHORS_AFTER_SAVE = 3;
    private static final String AUTHOR_NAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Orwell";
    private static final int EXPECTED_COUNT_AUTHORS = 2;
    private static final String AUTHOR_NAME_3 = "Tolstoy";
    @Qualifier("authorDaoJdbc")
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

    @Test
    public void shouldSaveAuthor() {
        authorDao.saveAuthor(new Author(null, AUTHOR_NAME_3));

        List<Author> allAuthors = authorDao.getAllAuthors();

        assertThat(allAuthors.stream()
            .map(Author::getName))
            .hasSize(EXPECTED_COUNT_AUTHORS_AFTER_SAVE)
            .contains(AUTHOR_NAME_1, AUTHOR_NAME_2, AUTHOR_NAME_3);
    }
}