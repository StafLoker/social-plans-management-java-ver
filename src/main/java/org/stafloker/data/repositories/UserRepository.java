package org.stafloker.data.repositories;

import org.stafloker.data.models.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User> {
    Optional<User> searchByName(String name);

    Optional<User> searchByMobile(String mobileNumber);
}
