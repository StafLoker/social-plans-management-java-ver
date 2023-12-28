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
            Serializable id = session.save(entity);
            session.getTransaction().commit();

            @SuppressWarnings("unchecked")
            T createdEntity = (T) session.find(entity.getClass(), id);
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
    public Optional<T> read(Long id) {
        try (Session session = this.sessionFactory.openSession()) {
            T entity = session.find(this.getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (HibernateException e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<T> entity = this.read(id);
            entity.ifPresent(session::delete);
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

    public void deleteAll() {
        try (Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM " + this.getEntityClass().getSimpleName()).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Hibernate: " + e);
        }
    }

    protected abstract Class<T> getEntityClass();
}
