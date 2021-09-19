package ru.alaev.library_application.service;

import java.util.stream.Collectors;
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

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final StyleDao styleDao;

    @Override
    public boolean addBookOrReturnFalse(String bookName, String authorName, String styleName)
        throws StyleNotFoundException, AuthorNotFoundException {
        final long authorId = authorDao.getAuthorIdByName(authorName)
            .orElseThrow(() -> new AuthorNotFoundException(authorName));
        final long styleId = styleDao.getStyleIdByName(styleName)
            .orElseThrow(() -> new StyleNotFoundException(styleName));

        final Book checkedBook = new Book(null, bookName, new Author(authorId, authorName),
            new Style(styleId, styleName));

        if (bookDao.isExistBook(checkedBook)) {
            return false;
        }
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
        if (styleDao.isExistStyle(name)) {
            return false;
        }

        styleDao.saveStyle(new Style(name));
        return true;
    }

    @Override
    public String getAllBooksInOneString() {
        return bookDao.getAllBooks().stream()
            .map(book -> book.getName() + " " + book.getStyle().getName() + " " + book.getAuthor()
                .getName())
            .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean addNewAuthorOrReturnFalse(String name) {
        if (authorDao.isExistAuthor(name)) {
            return false;
        }

        authorDao.saveAuthor(new Author(null, name));
        return true;
    }
}
