package org.stafloker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.User;
import org.stafloker.services.exceptions.DuplicateException;
import org.stafloker.services.exceptions.SecurityAuthorizationException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Session session;

    @Autowired
    public UserService(UserRepository userRepository, Session session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    public User create(String username, String password, Integer age, String mobile) {
        User user = User.builder()
                .name(username)
                .password(password)
                .age(age)
                .mobile(mobile)
                .build();
        this.userRepository.findByName(user.getName())
                .ifPresent(existingUser -> {
                    throw new DuplicateException("The username already exists: " + user.getName());
                });
        this.userRepository.findByMobile(user.getMobile())
                .ifPresent(existingUser -> {
                    throw new DuplicateException("The mobile number already exists: " + user.getMobile());
                });
        return this.userRepository.create(user);
    }

    public void logIn(String username, String password) {
        this.session.setLoggedUser(this.userRepository.findByName(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new SecurityAuthorizationException("Unauthorized credentials")));
    }

    public void logOut() {
        this.session.setLoggedUser(null);
    }
}

