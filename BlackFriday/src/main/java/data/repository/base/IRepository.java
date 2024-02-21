package data.repository.base;

import data.model.entity.base.EntityBase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<T extends EntityBase> {
    @Transactional
    T create(T entity);
    @Transactional
    T update(T entity);
    Optional<T> getById(Class<T> clazz, UUID id);
    List<T> getAll(Class<T> clazz);
    @Transactional
    void deleteById(Class<T> clazz, UUID id);
}