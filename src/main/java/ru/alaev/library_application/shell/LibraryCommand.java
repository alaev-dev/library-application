package ru.alaev.library_application.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.alaev.library_application.service.LibraryService;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommand {
    private final LibraryService service;

    @ShellMethod(key = "add_B", value = "add new book")
    public String addNewBook(String bookName, String author, String style) {
        return service.addBookOrReturnFalse(bookName, author, style) ? "successfully" : "fail: maybe this book is already exist";
    }

    @ShellMethod(key = "list_A", value = "list all author")
    public String listAuthors() {
        return service.listAllAuthors();
    }

}
