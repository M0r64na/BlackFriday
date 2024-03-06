package data.repository.base;

import data.model.entity.base.EntityBase;
import data.util.SessionFactoryProvider;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Generic base class for common data access operations
public abstract class RepositoryBase<T extends EntityBase> implements IRepository<T> {
    protected static final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected RepositoryBase() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create(T entity) throws HibernateException {
        Session newSession = sessionFactory.openSession();

        newSession.beginTransaction();
        newSession.persist(entity);
        newSession.getTransaction().commit();

        newSession.close();
    }

    @Override
    public T update(T entity) throws HibernateException {
        Session newSession = sessionFactory.openSession();

        newSession.beginTransaction();
        T updated = newSession.merge(entity);
        newSession.getTransaction().commit();

        newSession.close();

        return updated;
    }

    @Override
    public Optional<T> getById(UUID id) throws HibernateException {
        Session newSession = sessionFactory.openSession();
        T entity = newSession.get(this.clazz, id);
        newSession.close();

        return Optional.of(entity);
    }

    @Override
    public List<T> getAll() throws HibernateException {
        Session newSession = sessionFactory.openSession();

        CriteriaQuery<T> createQuery = newSession.getCriteriaBuilder()
                .createQuery(this.clazz);
        Root<T> root = createQuery.from(this.clazz);
        CriteriaQuery<T> all = createQuery.select(root);
        TypedQuery<T> allQuery = newSession.createQuery(all);
        List<T> res = allQuery.getResultList();

        newSession.close();

        return res;
    }

    @Override
    public void deleteById(UUID id) throws HibernateException {
        Optional<T> entity = this.getById(id);

        Session newSession = sessionFactory.openSession();;

        newSession.beginTransaction();
        newSession.remove(entity.orElseThrow());
        newSession.getTransaction().commit();

        newSession.close();
    }
}