package ru.alaev.library_application.dao;

import ru.alaev.library_application.domain.Style;

import java.util.List;
import java.util.Optional;

public interface StyleDao {
    Optional<Long> getStyleIdByName(String style);

    List<Style> getAllStyles();

    boolean isExistStyle(String styleName);

    void save(Style style);

    Optional<String> getStyleNameById(long idStyle);
}
