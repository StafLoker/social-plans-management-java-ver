package org.stafloker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.console.Session;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.User;
import org.stafloker.services.exceptions.DuplicateException;
import org.stafloker.services.exceptions.SecurityAuthorizationException;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class UserServiceTest {

    @Autowired
    private Session session;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        this.session.setLoggedUser(this.userRepository.read(1L).get());
    }

    @Test
    void testCreate() {
        assertThrows(DuplicateException.class, () -> this.userService.create(User.builder().name("root").password("root").age(19).mobile("123456789").build()));
        assertThrows(DuplicateException.class, () -> this.userService.create(User.builder().name("Pepe").password("123").age(19).mobile("111111111").build()));
    }

    @Test
    void testLogin() {
        assertThrows(SecurityAuthorizationException.class, () -> this.userService.login("NonExistent", "123"));
    }
}