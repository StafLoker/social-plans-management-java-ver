package org.stafloker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.User;
import org.stafloker.services.exceptions.DuplicateException;
import org.stafloker.services.exceptions.SecurityAuthorizationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(DuplicateException.class, () -> this.userService.create("root", "root", 19, "123456789"));
        assertThrows(DuplicateException.class, () -> this.userService.create("Pepe", "123", 19, "111111111"));
    }

    @Test
    void testLogIn() {
        assertThrows(SecurityAuthorizationException.class, () -> this.userService.logIn("NonExistent", "123"));
    }

    @Test
    void testLogOut() {
        this.session.setLoggedUser(User.builder().name("Test").password("123").age(45).mobile("111111111").build());
        this.userService.logOut();
        assertThrows(SecurityAuthorizationException.class, () -> this.session.assertLogin());
    }
}