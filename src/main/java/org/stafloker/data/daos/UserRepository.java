package org.stafloker.data.daos;

import org.springframework.stereotype.Repository;
import org.stafloker.data.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User> {

    Optional<User> findByName(String name);

    Optional<User> findByMobile(String mobileNumber);
}
