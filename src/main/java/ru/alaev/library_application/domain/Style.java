package ru.alaev.library_application.domain;

import lombok.Data;

@Data
public class Style {
    private long id;
    private String name;

    public Style(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Style(String name) {
        this.name = name;
    }
}
