package ru.alaev.library_application.dao;

import java.util.List;
import java.util.Optional;
import ru.alaev.library_application.domain.Style;

public interface StyleDao {

    Optional<Long> getStyleIdByName(String style);

    List<Style> getAllStyles();

    boolean isExistStyle(String styleName);

    Style saveStyle(Style style);

    void updateStyle(Style style);

    Optional<String> getStyleNameById(long idStyle);
}
