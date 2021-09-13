package ru.alaev.library_application.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Author> getAuthorById(long id) {
        final List<Author> authors = jdbc.query("select * from author where id_author =:idAuthor",
                                                Map.of("idAuthor", id),
                                                new AuthorMapper());

        return authors.size() == 0 ? Optional.empty() : Optional.of(authors.get(0));
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("select * from author", new AuthorMapper());
    }

    @Override
    public void saveAuthor(Author author) {

    }

    @Override
    public void updateAuthor(Author author) {

    }

    @Override
    public void deleteAuthor(Author author) {

    }

    @Override
    public Optional<Long> getAuthorIdByName(String author) {
        Map<String, Object> params = Collections.singletonMap("author_name", author);

        final List<Author> authors =
                jdbc.query("select * from author where author_name = :author_name", params, new AuthorMapper());

        return authors.size() == 0 ? Optional.empty() : Optional.of(authors.get(0).getId());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id_author"), rs.getString("author_name"));
        }
    }
}
