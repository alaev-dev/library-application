package ru.alaev.library_application.dao.jpa.hibernate;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.BookDao;
import ru.alaev.library_application.domain.Book;

@ConditionalOnBean(value = HibernateConfig.class)
@Repository
public class BookDaoJPA implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Book> getBookById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getAllBooks() {
        final TypedQuery<Book> query = entityManager.createQuery(
            "select b "
                + "from Book b "
                + "     join fetch b.author "
                + "     join fetch b.style",
            Book.class);

        return query.getResultList();
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void deleteBook(Book book) {
        entityManager.remove(book);
    }

    @Override
    public boolean isExistBook(Book book) {
        final TypedQuery<BookDao> query = entityManager.createQuery(
            "SELECT B "
                + "FROM Book B "
                + "     JOIN FETCH B.style JOIN FETCH B.author "
                + "WHERE B.style.name = :style_name "
                + "  AND B.author.name = :author_name",
            BookDao.class);

        query.setParameter("style_name", book.getStyle().getName());
        query.setParameter("author_name", book.getAuthor().getName());

        final List<BookDao> resultList = query.getResultList();

        return !resultList.isEmpty();
    }
}
