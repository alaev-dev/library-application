package ru.alaev.library_application.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.domain.Author;
import ru.alaev.library_application.service.execption.AuthorNotFoundException;

@ConditionalOnBean(value = JDBCConfig.class)
@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Author> getAuthorById(long id) {
        final List<Author> authors = jdbc.query(
            "SELECT * "
                + "FROM AUTHORS "
                + "WHERE ID = :ID",
            Map.of("ID", id),
            new AuthorMapper());

        return authors.size() == 0 ? Optional.empty() : Optional.of(authors.get(0));
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query(
            "SELECT * "
                + "FROM AUTHORS",
            new AuthorMapper());
    }

    @Override
    public Author saveAuthor(Author author) {
        jdbc.update("INSERT INTO AUTHORS (NAME) VALUES (:AUTHOR_NAME)",
            Map.of("AUTHOR_NAME", author.getName()));

        author.setId(
            getAuthorIdByName(author.getName()).orElseThrow(() -> new AuthorNotFoundException(
                author.getName())));

        return author;
    }

    @Override
    public void updateAuthor(Author author) {

    }

    @Override
    public void deleteAuthor(Author author) {

    }

    @Override
    public Optional<Long> getAuthorIdByName(String author) {
        Map<String, Object> params = Collections.singletonMap("AUTHOR_NAME", author);

        final List<Author> authors =
            jdbc.query(
                "SELECT *"
                    + "FROM AUTHORS "
                    + "WHERE NAME = :AUTHOR_NAME",
                params,
                new AuthorMapper());

        return authors.size() == 0 ? Optional.empty() : Optional.of(authors.get(0).getId());
    }

    @Override
    public boolean isExistAuthor(String name) {
        return getAuthorIdByName(name).isPresent();
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    }
}
