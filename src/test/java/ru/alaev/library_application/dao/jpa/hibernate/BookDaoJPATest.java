package ru.alaev.library_application.dao.jpa.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.domain.Book;

@DataJpaTest
@ComponentScan("ru.alaev.library_application.dao.jpa")
class BookDaoJPATest {

    @Qualifier("bookDaoJPA")
    @Autowired
    BookDao bookDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getAllBooks() {
        final SessionFactory sessionFactory = entityManager.getEntityManager()
            .getEntityManagerFactory()
            .unwrap(SessionFactory.class);

        sessionFactory.getStatistics().setStatisticsEnabled(true);

        final List<Book> allBooks = bookDao.getAllBooks();

        assertThat(allBooks).isNotNull().hasSize(2);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    void saveBook() {
    }
}