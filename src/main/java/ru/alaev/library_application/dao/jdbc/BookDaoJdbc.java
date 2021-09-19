package ru.alaev.library_application.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.domain.Author;
import ru.alaev.library_application.domain.Book;
import ru.alaev.library_application.domain.Style;

@ConditionalOnBean(value = JDBCConfig.class)
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> getBookById(long id) {
        final List<Book> books = jdbc.query(
            "SELECT B.id, B.name book_name, AU.name author_name, author_id, S.name style_name, style_id "
                + "FROM BOOKS B "
                + "         LEFT JOIN AUTHORS AU ON B.AUTHOR_ID = AU.ID "
                + "         LEFT JOIN STYLES S ON S.ID = B.STYLE_ID "
                + "WHERE B.id = :ID",
            Map.of("ID", id), new BookMapper());

        return books.size() == 0 ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query(
            "SELECT B.id, B.name book_name, AU.name author_name, author_id, S.name style_name, style_id "
                + "FROM BOOKS B "
                + "         LEFT JOIN AUTHORS AU ON B.AUTHOR_ID = AU.ID "
                + "         LEFT JOIN STYLES S ON S.ID = B.STYLE_ID",
            new BookMapper());
    }

    @Override
    public Book saveBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("NAME", book.getName());
        params.put("AUTHOR_ID", book.getAuthor().getId());
        params.put("STYLE_ID", book.getStyle().getId());

        jdbc.update(
            "INSERT INTO BOOKS(NAME, AUTHOR_ID, STYLE_ID) "
                + "VALUES (:NAME, :AUTHOR_ID, :STYLE_ID)",
            params);

        return book;
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void deleteBook(Book book) {

    }

    @Override
    public boolean isExistBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("NAME", book.getName());
        params.put("AUTHOR_NAME", book.getAuthor().getName());
        params.put("STYLE_NAME", book.getStyle().getName());

        final Integer count_rows = jdbc.queryForObject(
            "SELECT COUNT(B.ID) "
                + "FROM BOOKS B "
                + "         LEFT JOIN AUTHORS AU ON B.AUTHOR_ID = AU.ID "
                + "         LEFT JOIN STYLES S ON B.STYLE_ID = S.ID "
                + "WHERE B.NAME = :NAME"
                + "  AND AU.NAME = :AUTHOR_NAME"
                + "  AND S.NAME = :STYLE_NAME",
            params,
            Integer.class);

        return count_rows != 0;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                rs.getLong("id"),
                rs.getString("book_name"),
                new Author(rs.getLong("author_id"), rs.getString("author_name")),
                new Style(rs.getLong("style_id"), rs.getString("style_name"))
            );
        }
    }
}
