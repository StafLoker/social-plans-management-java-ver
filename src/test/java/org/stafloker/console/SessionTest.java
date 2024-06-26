package org.stafloker.console;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.services.Session;
import org.stafloker.services.exceptions.SecurityAuthorizationException;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class SessionTest {

    @Autowired
    private Session session;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testAssertLogin() {
        assertThrows(SecurityAuthorizationException.class, this.session::assertLogin);
        this.session.setLoggedUser(this.userRepository.read(1L).get());
        assertDoesNotThrow(this.session::assertLogin);
        this.session.setLoggedUser(null);
    }

    @Test
    void testGetSecuredUser() {
        this.session.setLoggedUser(this.userRepository.read(1L).get());
        assertEquals(this.userRepository.read(1L).get(), this.session.getSecuredUser());
        this.session.setLoggedUser(null);
    }
}