package ru.alaev.library_application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.domain.Author;
import ru.alaev.library_application.domain.Book;
import ru.alaev.library_application.domain.Style;
import ru.alaev.library_application.service.execption.AuthorNotFoundException;
import ru.alaev.library_application.service.execption.StyleNotFoundException;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final StyleDao styleDao;

    @Override
    public boolean addBookOrReturnFalse(String bookName, String author, String style) throws StyleNotFoundException, AuthorNotFoundException {
        final long authorId = authorDao.getAuthorIdByName(author).orElseThrow(() -> new AuthorNotFoundException(author));
        final long styleId = styleDao.getStyleIdByName(style).orElseThrow(() -> new StyleNotFoundException(style));

        Book checkedBook = new Book(bookName, authorId, styleId);

        if (bookDao.isExistBook(checkedBook)) return false;
        bookDao.saveBook(checkedBook);
        return true;
    }

    @Override
    public String getAllAuthorsInOneString() {
        return authorDao.getAllAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getAllStylesInOneString() {
        return styleDao.getAllStyles().stream()
                .map(Style::getName)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean addNewStyleOrReturnFalse(String name) {
        if (styleDao.isExistStyle(name)) return false;

        styleDao.save(new Style(name));
        return true;
    }

    @Override
    public String getAllBooksInOneString() {
        return bookDao.getAllBooks().stream()
                .map(book -> book.getName() + " " +
                        styleDao.getStyleNameById(book.getIdStyle())
                                .orElseThrow(
                                        () -> new StyleNotFoundException(String.valueOf(book.getIdStyle()))) + " " +
                        authorDao.getAuthorById(book.getIdAuthor())
                                .orElseThrow(() -> new AuthorNotFoundException(String.valueOf(book.getIdAuthor())))
                                .getName())
                .collect(Collectors.joining("\n"));
    }
}
