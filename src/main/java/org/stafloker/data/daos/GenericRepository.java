package org.stafloker.data.daos;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    T create(T entity);

    T update(T entity);

    Optional<T> read(Long id);

    void deleteById(Long id);

    List<T> findAll();

    void deleteAll();
}
