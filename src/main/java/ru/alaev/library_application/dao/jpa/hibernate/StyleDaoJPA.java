package ru.alaev.library_application.dao.jpa.hibernate;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import ru.alaev.library_application.dao.StyleDao;
import ru.alaev.library_application.domain.Author;
import ru.alaev.library_application.domain.Style;

@ConditionalOnBean(value = HibernateConfig.class)
@Repository
public class StyleDaoJPA implements StyleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Long> getStyleIdByName(String styleName) {
        final TypedQuery<Author> query = entityManager.createQuery(
            "SELECT S "
                + "FROM Style S "
                + "WHERE S.name = :NAME",
            Author.class);

        query.setParameter("NAME", styleName);

        final List<Author> resultList = query.getResultList();

        return Optional.of(resultList.get(0).getId());
    }

    @Override
    public List<Style> getAllStyles() {
        final TypedQuery<Style> query = entityManager.createQuery(
            "SELECT S FROM Style S", Style.class);

        return query.getResultList();
    }

    @Override
    public boolean isExistStyle(String styleName) {
        return getStyleIdByName(styleName).isPresent();
    }

    @Override
    public Style saveStyle(Style style) {
        if (style.getId() == null) {
            entityManager.persist(style);
            return style;
        } else {
            return entityManager.merge(style);
        }
    }

    @Override
    public void updateStyle(Style style) {
        final TypedQuery<Style> query = entityManager.createQuery(
            "UPDATE Style S "
                + "SET S.name = :NAME "
                + "WHERE S.id = :ID",
            Style.class);

        query.setParameter("NAME", style.getName());
        query.setParameter("ID", style.getId());

        query.executeUpdate();
    }

    @Override
    public Optional<String> getStyleNameById(long idStyle) {
        return Optional.of(entityManager.find(Style.class, idStyle).getName());
    }
}
