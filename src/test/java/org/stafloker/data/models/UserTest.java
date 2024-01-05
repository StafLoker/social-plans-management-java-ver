package org.stafloker.data.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

@TestConfig
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void setPassword() {
        assertThrows(InvalidAttributeException.class, () -> this.userRepository.read(1L).get().setPassword("12"));

    }

    @Test
    void setAge() {
        assertThrows(InvalidAttributeException.class, () -> this.userRepository.read(1L).get().setAge(101));
    }

}