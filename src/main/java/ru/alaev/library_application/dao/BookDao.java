package ru.alaev.library_application.dao;

import java.util.List;
import java.util.Optional;
import ru.alaev.library_application.domain.Book;

public interface BookDao {
    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    Book saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    boolean isExistBook(Book book);
}
