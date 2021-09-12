package ru.alaev.library_application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.domain.Author;
import ru.alaev.library_application.domain.Book;
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
    public String listAllAuthors() {
        return authorDao.getAllAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.joining("\n"));
    }
}
