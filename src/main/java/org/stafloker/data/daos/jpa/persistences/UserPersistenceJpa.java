package org.stafloker.data.daos.jpa.persistences;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.stafloker.data.daos.jpa.entities.UserEntity;
import org.stafloker.data.models.User;

import java.util.Optional;

public interface UserPersistenceJpa extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.name = :name")
    Optional<User> findByName(@Param("name") String name);

    @Query("SELECT u FROM UserEntity u WHERE u.mobile = :mobileNumber")
    Optional<User> findByMobile(@Param("mobileNumber") String mobileNumber);
}
