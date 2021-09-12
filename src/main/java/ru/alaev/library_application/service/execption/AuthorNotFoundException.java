package ru.alaev.library_application.service.execption;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String author) {
        super(String.format("There is no author with this name %s", author));
    }
}
