package data.repository.base;

import data.model.entity.base.EntityBase;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<T extends EntityBase> {
    void create(T entity) throws HibernateException;
    T update(T entity) throws HibernateException;
    Optional<T> getById(UUID id) throws HibernateException;
    List<T> getAll() throws HibernateException;
    void deleteById(UUID id) throws HibernateException;
}