package org.stafloker.data.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.models.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByName() {
        assertTrue(this.userRepository.findByName("root").isPresent());

        assertFalse(this.userRepository.findByName("Test").isPresent());
    }

    @Test
    void testFindByMobile() {
        assertTrue(this.userRepository.findByMobile("111111111").isPresent());

        assertFalse(this.userRepository.findByMobile("111111112").isPresent());
    }

    @Test
    void testCreate() {
        User user = this.userRepository.create(User.builder().name("test").password("test").age(19).mobile("123456789").build());
        assertTrue(this.userRepository.findByName(user.getName()).isPresent());
        this.userRepository.deleteById(user.getId());
    }

    @Test
    void testUpdate() {
        User user = this.userRepository.read(6L).get();
        user.setName("Test");
        this.userRepository.update(user);
        assertTrue(this.userRepository.findByName("Test").isPresent());
        user.setName("User5");
        this.userRepository.update(user);
    }

    @Test
    void testRead() {
        assertTrue(this.userRepository.read(1L).isPresent());

        assertFalse(this.userRepository.read(50L).isPresent());
    }

    @Test
    void testDelete() {
        User user = this.userRepository.create(User.builder().name("test").password("test").age(19).mobile("123456789").build());
        this.userRepository.deleteById(user.getId());
        assertFalse(this.userRepository.findByName(user.getName()).isPresent());
    }

    @Test
    void testGetAll() {
        User user = this.userRepository.create(User.builder().name("test").password("test").age(19).mobile("123456789").build());
        List<User> list = this.userRepository.findAll();
        assertTrue(list.contains(user));
        this.userRepository.deleteById(user.getId());
    }

}