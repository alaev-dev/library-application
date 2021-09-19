package ru.alaev.library_application.dao.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.domain.Author;
import ru.alaev.library_application.domain.Book;
import ru.alaev.library_application.domain.Style;

@JdbcTest
@ComponentScan("ru.alaev.library_application.dao.jdbc")
class BookDaoJdbcTest {

    public static final String ORWELL_AUTHOR_NAME = "Orwell";
    public static final String ORWELL_STYLE_NAME = "Novel";
    private static final int EXPECTED_COUNT_BOOKS = 2;
    private static final String INSERT_BOOK_NAME = "Poltava";
    private static final long INSERT_AUTHOR_ID = 1L;
    private static final long INSERT_STYLE_ID = 1L;
    private static final int INSERT_BOOK_ID = 3;
    private static final int ORWELL_BOOK_ID = 1;
    private static final String ORWELL_BOOK_NAME = "1984";
    private static final long ORWELL_AUTHOR_ID = 2L;
    private static final long ORWELL_STYLE_ID = 1L;
    private static final String ORWELL_WRONG_BOOK_NAME = "1985";
    private static final long WRONG_BOOK_ID = 1984L;
    @Qualifier("bookDaoJdbc")
    @Autowired
    BookDao bookDao;

    @Test
    void shouldReturnAllBooks() {
        final List<Book> allBooks = bookDao.getAllBooks();

        assertThat(allBooks.stream().map(Book::getName))
            .hasSize(EXPECTED_COUNT_BOOKS);
    }

    @Test
    void shouldReturnBookById() {
        final Optional<Book> bookById = bookDao.getBookById(ORWELL_BOOK_ID);

        assertThat(bookById).isPresent();
        assertThat(bookById.get())
            .hasFieldOrPropertyWithValue("name", ORWELL_BOOK_NAME)
            .hasFieldOrPropertyWithValue("author", new Author(ORWELL_AUTHOR_ID, ORWELL_AUTHOR_NAME))
            .hasFieldOrPropertyWithValue("style", new Style(ORWELL_STYLE_ID, ORWELL_STYLE_NAME));
    }

    @Test
    void shouldReturnOptionalEmptyIfBookIdWrong() {
        final Optional<Book> bookById = bookDao.getBookById(WRONG_BOOK_ID);

        assertThat(bookById).isEmpty();
    }

    @Test
    void shouldSaveNewBook() {
        bookDao.saveBook(new Book(null, INSERT_BOOK_NAME, new Author(INSERT_AUTHOR_ID, ""),
            new Style(INSERT_STYLE_ID, "")));

        final Optional<Book> bookById = bookDao.getBookById(INSERT_BOOK_ID);

        assertThat(bookById).isPresent().get()
            .hasFieldOrPropertyWithValue("name", INSERT_BOOK_NAME)
            .hasFieldOrPropertyWithValue("author", new Author(INSERT_AUTHOR_ID, "Pushkin"))
            .hasFieldOrPropertyWithValue("style", new Style(INSERT_STYLE_ID, "Novel"));
    }

    @Test
    void shouldCheckExistenceBook() {
        final boolean isExistBook = bookDao.isExistBook(
            new Book(null, ORWELL_BOOK_NAME, new Author(null, ORWELL_AUTHOR_NAME),
                new Style(null, ORWELL_STYLE_NAME)));

        assertThat(isExistBook).isTrue();
    }

    @Test
    void shouldReturnFalseIfBookNotExist() {
        final boolean isExistBook = bookDao.isExistBook(
            new Book(null, ORWELL_WRONG_BOOK_NAME, new Author(null, ORWELL_AUTHOR_NAME),
                new Style(null, ORWELL_STYLE_NAME)));

        assertThat(isExistBook).isFalse();
    }
}