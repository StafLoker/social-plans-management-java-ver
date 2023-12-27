package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.Session;
import org.stafloker.data.repositories.GenericRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepositorySql<T> implements GenericRepository<T> {

    protected final Session session;

    protected GenericRepositorySql(Session session){
        this.session = session;
    }

    @Override
    public T create(T entity) {
        session.beginTransaction();
        Serializable id = (Serializable) session.save(entity);
        session.getTransaction().commit();

        @SuppressWarnings("unchecked")
        T createdEntity = (T) session.get(entity.getClass(), id);
        return createdEntity;
    }

    @Override
    public T update(T entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    public Optional<T> read(Integer id) {
        T entity = session.get(this.getEntityClass(), id);
        return Optional.ofNullable(entity);
    }

    @Override
    public void deleteById(Integer id) {
        session.beginTransaction();
        T entity = session.get(this.getEntityClass(), id);
        if (entity != null) {
            session.delete(entity);
        }
        session.getTransaction().commit();
    }

    @Override
    public List<T> findAll() {
        return session.createQuery("FROM " + this.getEntityClass().getSimpleName(), this.getEntityClass()).list();
    }

    protected abstract Class<T> getEntityClass();
}
