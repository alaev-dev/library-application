package ru.alaev.library_application.dao.jpa.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.domain.Author;

@DataJpaTest
@ComponentScan("ru.alaev.library_application.dao.jpa")
class AuthorDaoJPATest {

    public static final long FIRST_AUTHOR_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    @Qualifier("authorDaoJPA")
    @Autowired
    AuthorDao authorDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getAuthorById() {
        final Optional<Author> authorById = authorDao.getAuthorById(FIRST_AUTHOR_ID);
        final Author exceptedAuthor = entityManager.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(authorById).isPresent().get()
            .usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }

    @Test
    void getAllAuthors() {
        final SessionFactory sessionFactory = entityManager.getEntityManager()
            .getEntityManagerFactory()
            .unwrap(SessionFactory.class);

        sessionFactory.getStatistics().setStatisticsEnabled(true);

        final List<Author> allAuthors = authorDao.getAllAuthors();

        assertThat(allAuthors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
            .allMatch(author -> author.getId() != null)
            .allMatch(author -> !author.getName().equals(""));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }
}