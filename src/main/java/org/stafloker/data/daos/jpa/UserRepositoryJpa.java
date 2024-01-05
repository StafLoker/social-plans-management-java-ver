package org.stafloker.data.daos.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.daos.jpa.persistences.UserPersistenceJpa;
import org.stafloker.data.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJpa implements UserRepository {

    private final UserPersistenceJpa userPersistenceJpa;

    @Autowired
    public UserRepositoryJpa(UserPersistenceJpa userPersistenceJpa) {
        this.userPersistenceJpa = userPersistenceJpa;
    }

    @Override
    public Optional<User> findByName(String name) {
        return this.userPersistenceJpa.findByName(name);
    }

    @Override
    public Optional<User> findByMobile(String mobileNumber) {
        return this.userPersistenceJpa.findByMobile(mobileNumber);
    }

    @Override
    public User create(User entity) {
        return this.userPersistenceJpa.save(entity);
    }

    @Override
    public User update(User entity) {
        return this.userPersistenceJpa.save(entity);
    }

    @Override
    public Optional<User> read(Long id) {
        return this.userPersistenceJpa.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.userPersistenceJpa.deleteById(id);
    }

    @Override
    public List<User> saveAll(List<User> entitiesList) {
        return this.userPersistenceJpa.saveAll(entitiesList);
    }

    @Override
    public void deleteAll() {
        this.userPersistenceJpa.deleteAll();
    }

    @Override
    public List<User> findAll() {
        return this.userPersistenceJpa.findAll();
    }
}
