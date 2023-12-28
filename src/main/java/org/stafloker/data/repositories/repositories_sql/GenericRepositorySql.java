package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.stafloker.data.repositories.GenericRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepositorySql<T> implements GenericRepository<T> {

    protected final SessionFactory sessionFactory;

    protected GenericRepositorySql(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T create(T entity) {
        try (Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();
            Serializable id = (Serializable) session.save(entity);
            session.getTransaction().commit();

            @SuppressWarnings("unchecked")
            T createdEntity = (T) session.get(entity.getClass(), id);
            return createdEntity;
        } catch (HibernateException e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    @Override
    public T update(T entity) {
        try (Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return entity;
        } catch (HibernateException e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    @Override
    public Optional<T> read(Integer id) {
        try (Session session = this.sessionFactory.openSession()) {
            T entity = session.get(this.getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (HibernateException e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = session.get(this.getEntityClass(), id);
            if (entity != null) {
                session.delete(entity);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("FROM " + this.getEntityClass().getSimpleName(), this.getEntityClass()).list();
        } catch (HibernateException e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    protected abstract Class<T> getEntityClass();
}
