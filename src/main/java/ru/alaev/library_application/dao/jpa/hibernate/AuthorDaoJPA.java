package ru.alaev.library_application.dao.jpa.hibernate;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.AuthorDao;
import ru.alaev.library_application.domain.Author;

@ConditionalOnBean(value = HibernateConfig.class)
@Repository
public class AuthorDaoJPA implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Author> getAuthorById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> getAllAuthors() {
        final TypedQuery<Author> query = entityManager.createQuery(
            "SELECT AU FROM Author AU", Author.class);

        return query.getResultList();
    }

    @Override
    public Author saveAuthor(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public void updateAuthor(Author author) {
        final Query query = entityManager.createQuery(
            "UPDATE Author AU "
                + "SET AU.name = :NAME "
                + "WHERE AU.id = :ID");

        query.setParameter("NAME", author.getName());
        query.setParameter("ID", author.getId());

        query.executeUpdate();
    }

    @Override
    public void deleteAuthor(Author author) {
        /*final Query query = entityManager.createQuery(
            "DELETE "
                + "FROM Author AU "
                + "WHERE AU.id = :ID");

        query.setParameter("ID", author.getId());
        query.executeUpdate();*/

        entityManager.remove(author);
    }

    @Override
    public Optional<Long> getAuthorIdByName(String author) {
        final TypedQuery<Author> query = entityManager.createQuery(
            "SELECT AU "
                + "FROM Author AU "
                + "WHERE AU.name = :NAME", Author.class);

        query.setParameter("NAME", author);
        final List<Author> resultList = query.getResultList();

        return Optional.of(resultList.get(0).getId());
    }

    @Override
    public boolean isExistAuthor(String name) {
        return getAuthorIdByName(name).isPresent();
    }
}
