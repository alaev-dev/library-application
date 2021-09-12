package ru.alaev.library_application.dao;

import ru.alaev.library_application.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getAuthorById(long id);

    List<Author> getAllAuthors();

    void saveAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Author author);

    Optional<Long> getAuthorIdByName(String author);
}
