package ru.alaev.library_application.dao;

import java.util.Optional;

public interface StyleDao {
    Optional<Long> getStyleIdByName(String style);
}
