package data.repository.base;

import data.model.entity.base.EntityBase;
import data.util.SessionFactoryProvider;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Generic base class for common data access operations
public class RepositoryBase<T extends EntityBase> implements IRepository<T> {
    private static final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    @Override
    public T create(T entity) {
        Session newSession = sessionFactory.openSession();

        newSession.beginTransaction();
        newSession.persist(entity);
        newSession.getTransaction().commit();

        newSession.close();

        return entity;
    }

    @Override
    public T update(T entity) {
        Session newSession = sessionFactory.openSession();

        newSession.beginTransaction();
        newSession.persist(entity);
        newSession.getTransaction().commit();

        newSession.close();

        return entity;
    }

    @Override
    public Optional<T> getById(Class<T> clazz, UUID id) {
        Session newSession = sessionFactory.openSession();
        T entity = newSession.get(clazz, id);
        newSession.close();

        return Optional.of(entity);
    }

    @Override
    public List<T> getAll(Class<T> clazz) {
        Session newSession = sessionFactory.openSession();

        CriteriaQuery<T> createQuery = newSession.getCriteriaBuilder()
                .createQuery(clazz);
        Root<T> root = createQuery.from(clazz);
        CriteriaQuery<T> all = createQuery.select(root);

        TypedQuery<T> allQuery = newSession.createQuery(all);

        newSession.close();

        return allQuery.getResultList();
    }

    @Override
    public void deleteById(Class<T> clazz, UUID id) {
        Optional<T> entity = this.getById(clazz, id);

        Session newSession = sessionFactory.openSession();;

        newSession.beginTransaction();
        newSession.remove(entity);
        newSession.getTransaction().commit();

        newSession.close();
    }
}
