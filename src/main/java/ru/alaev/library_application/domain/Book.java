package ru.alaev.library_application.domain;

import lombok.Data;

@Data
public class Book {
    private long id;
    private String name;
    private long idAuthor;
    private long idStyle;

    public Book(long id, String name, long idAuthor, long idStyle) {
        this.id = id;
        this.name = name;
        this.idAuthor = idAuthor;
        this.idStyle = idStyle;
    }

    public Book(String name, long idAuthor, long idStyle) {
        this.name = name;
        this.idAuthor = idAuthor;
        this.idStyle = idStyle;
    }
}
