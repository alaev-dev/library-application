package ru.alaev.library_application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.service.execption.AuthorNotFoundException;
import ru.alaev.library_application.service.execption.StyleNotFoundException;

@SpringBootTest(classes = LibraryServiceImpl.class)
class LibraryServiceImplTest {
    private static final long INSERT_ID_STYLE = 2L;
    private static final long INSERT_ID_AUTHOR = 1L;
    private static final String INSERT_NAME_AUTHOR = "Pushkin";
    private static final String INSERT_NAME_BOOK = "Ruslan and Ludmila";
    private static final String INSERT_NAME_STYLE = "Poem";

    @MockBean
    private AuthorDao authorDao;
    @MockBean
    private BookDao bookDao;
    @MockBean
    private StyleDao styleDao;
    @Autowired
    private LibraryService service;

    @Test
    void shouldDontSaveExistBook() {
        when(authorDao.getAuthorIdByName(INSERT_NAME_AUTHOR)).thenReturn(Optional.of(INSERT_ID_AUTHOR));
        when(styleDao.getStyleIdByName(INSERT_NAME_STYLE)).thenReturn(Optional.of(INSERT_ID_STYLE));

        when(bookDao.isExistBook(any())).thenReturn(true);

        final boolean isInsert = service.addBookOrReturnFalse(INSERT_NAME_BOOK, INSERT_NAME_AUTHOR, INSERT_NAME_STYLE);

        verify(bookDao, times(1)).isExistBook(any());
        verify(bookDao, times(0)).saveBook(any());

        assertThat(isInsert).isFalse();
    }

    @Test
    void shouldSaveNotExistBook() {
        when(authorDao.getAuthorIdByName(INSERT_NAME_AUTHOR)).thenReturn(Optional.of(INSERT_ID_AUTHOR));
        when(styleDao.getStyleIdByName(INSERT_NAME_STYLE)).thenReturn(Optional.of(INSERT_ID_STYLE));

        when(bookDao.isExistBook(any())).thenReturn(false);

        final boolean isInsert = service.addBookOrReturnFalse(INSERT_NAME_BOOK, INSERT_NAME_AUTHOR, INSERT_NAME_STYLE);

        verify(bookDao, times(1)).isExistBook(any());
//        verify(bookDao, times(1)).saveBook(new Book(INSERT_NAME_BOOK, INSERT_ID_AUTHOR, INSERT_ID_STYLE));

        assertThat(isInsert).isTrue();
    }

    @Test
    void shouldThrowAuthorNotFoundExceptionWhenAuthorNotExist() {
        when(authorDao.getAuthorIdByName(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addBookOrReturnFalse(INSERT_NAME_BOOK, INSERT_NAME_AUTHOR, INSERT_NAME_STYLE))
                .isInstanceOf(AuthorNotFoundException.class);
    }

    @Test
    void shouldThrowStyleNotFoundExceptionWhenStyleNotExist() {
        when(authorDao.getAuthorIdByName(any())).thenReturn(Optional.of(INSERT_ID_AUTHOR));
        when(styleDao.getStyleIdByName(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addBookOrReturnFalse(INSERT_NAME_BOOK, INSERT_NAME_AUTHOR, INSERT_NAME_STYLE))
                .isInstanceOf(StyleNotFoundException.class);
    }
}