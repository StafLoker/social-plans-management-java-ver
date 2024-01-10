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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
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

    public User login(String username, String password) {
        return this.userRepository.findByName(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new SecurityAuthorizationException("Unauthorized credentials"));
    }
}

