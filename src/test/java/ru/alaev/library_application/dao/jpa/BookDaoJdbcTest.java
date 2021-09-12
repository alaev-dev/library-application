package ru.alaev.library_application.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final int EXPECTED_COUNT_BOOKS = 2;
    private static final String INSERT_NAME_BOOK = "Poltava";
    private static final long INSERT_ID_AUTHOR = 1L;
    private static final long INSERT_ID_STYLE = 1L;
    private static final int INSERT_ID_BOOK = 3;
    private static final int ORWELL_ID_BOOK = 1;
    private static final String ORWELL_NAME_BOOK = "1984";
    private static final long ORWELL_ID_AUTHOR = 2L;
    private static final long ORWELL_ID_STYLE = 1L;
    private static final String ORWELL_WRONG_NAME_BOOK = "1985";
    private static final long WRONG_ID_BOOK = 1984L;
    @Autowired
    BookDao bookDao;

    @Test
    void shouldReturnAllBooks() {
        final List<Book> allBooks = bookDao.getAllBooks();

        assertThat(allBooks.stream())
                .hasSize(EXPECTED_COUNT_BOOKS);
    }

    @Test
    void shouldReturnBookById() {
        final Optional<Book> bookById = bookDao.getBookById(ORWELL_ID_BOOK);

        assertThat(bookById).isPresent();
        assertThat(bookById.get())
                .hasFieldOrPropertyWithValue("name", ORWELL_NAME_BOOK)
                .hasFieldOrPropertyWithValue("idAuthor", ORWELL_ID_AUTHOR)
                .hasFieldOrPropertyWithValue("idStyle", ORWELL_ID_STYLE);
    }

    @Test
    void shouldReturnOptionalEmptyIfBookIdWrong() {
        final Optional<Book> bookById = bookDao.getBookById(WRONG_ID_BOOK);

        assertThat(bookById).isEmpty();
    }

    @Test
    void shouldSaveNewBook() {
        bookDao.saveBook(new Book(INSERT_NAME_BOOK, INSERT_ID_AUTHOR, INSERT_ID_STYLE));

        final Optional<Book> bookById = bookDao.getBookById(INSERT_ID_BOOK);

        assertThat(bookById).isPresent();
        assertThat(bookById.get())
                .hasFieldOrPropertyWithValue("name", INSERT_NAME_BOOK)
                .hasFieldOrPropertyWithValue("idAuthor", INSERT_ID_AUTHOR)
                .hasFieldOrPropertyWithValue("idStyle", INSERT_ID_STYLE);
    }

    @Test
    void shouldCheckExistenceBook() {
        final boolean isExistBook = bookDao.isExistBook(new Book(ORWELL_NAME_BOOK, ORWELL_ID_AUTHOR, ORWELL_ID_STYLE));

        assertThat(isExistBook).isTrue();
    }

    @Test
    void shouldReturnFalseIfBookNotExist() {
        final boolean isExistBook = bookDao.isExistBook(new Book(ORWELL_WRONG_NAME_BOOK,
                ORWELL_ID_AUTHOR,
                ORWELL_ID_STYLE));

        assertThat(isExistBook).isFalse();
    }
}