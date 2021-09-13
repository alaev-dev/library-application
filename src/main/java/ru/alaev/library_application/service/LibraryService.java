package ru.alaev.library_application.service;

import ru.alaev.library_application.service.execption.AuthorNotFoundException;
import ru.alaev.library_application.service.execption.StyleNotFoundException;

public interface LibraryService {

    boolean addBookOrReturnFalse(String bookName, String author, String style) throws StyleNotFoundException, AuthorNotFoundException;

    String getAllAuthorsInOneString();

    String getAllStylesInOneString();

    boolean addNewStyleOrReturnFalse(String name);

    String getAllBooksInOneString();
}
