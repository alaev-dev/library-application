package ru.alaev.library_application.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        final List<Book> books = jdbc.query("select * from book where id_book = :id", params, new BookMapper());

        return books.size() == 0 ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("select * from book", new BookMapper());
    }

    @Override
    public void saveBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", book.getName());
        params.put("id_author", book.getIdAuthor());
        params.put("id_style", book.getIdStyle());

        jdbc.update("insert into book (b_name, id_author, id_style) values (:name, :id_author, :id_style)", params);
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
        params.put("name", book.getName());
        params.put("id_author", book.getIdAuthor());
        params.put("id_style", book.getIdStyle());

        final List<Book> books = jdbc.query(
                "select * from book where b_name = :name and id_author = :id_author and id_style = :id_style",
                params,
                new BookMapper());

        return books.size() != 0;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id_book"),
                    rs.getString("b_name"),
                    rs.getLong("id_author"),
                    rs.getLong("id_style"));
        }
    }
}
