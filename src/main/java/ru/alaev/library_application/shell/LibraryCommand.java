package ru.alaev.library_application.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.alaev.library_application.service.LibraryService;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommand {
    private final LibraryService service;

    @ShellMethod(key = "addBook", value = "add new book")
    public String addNewBook(String bookName, String author, String style) {
        return service.addBookOrReturnFalse(bookName, author,
                                            style) ? "successfully" : "fail: maybe this book is already exist";
    }

    @ShellMethod(key = "authors", value = "list all author")
    public String listAuthors() {
        return service.getAllAuthorsInOneString();
    }

    @ShellMethod(key = "styles", value = "list all styles")
    public String listStyle() {
        return service.getAllStylesInOneString();
    }

    @ShellMethod(key = "addStyle", value = "add new style")
    public String addNewStyle(String name) {
        return service.addNewStyleOrReturnFalse(name) ? "successfully"
            : "fail: maybe this style is already exist";
    }

    @ShellMethod(key = "addAuthor", value = "add new author")
    public String addNewAuthor(String name) {
        return service.addNewAuthorOrReturnFalse(name) ? "successfully"
            : "fail: maybe this style is already exist";
    }

    @ShellMethod(key = "books", value = "list all books")
    public String listBooks() {
        return service.getAllBooksInOneString();
    }
}
