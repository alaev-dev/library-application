package ru.alaev.library_application.dao;

import java.util.List;
import java.util.Optional;
import ru.alaev.library_application.domain.Author;

public interface AuthorDao {
    Optional<Author> getAuthorById(long id);

    List<Author> getAllAuthors();

    Author saveAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Author author);

    Optional<Long> getAuthorIdByName(String author);

    boolean isExistAuthor(String name);
}
