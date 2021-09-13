package ru.alaev.library_application.dao;

import ru.alaev.library_application.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    boolean isExistBook(Book book);
}
