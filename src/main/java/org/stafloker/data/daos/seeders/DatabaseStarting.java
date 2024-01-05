package org.stafloker.data.daos.seeders;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.stafloker.data.models.User;
import org.stafloker.data.daos.UserRepository;;

@Repository
public class DatabaseStarting {
    private final UserRepository userRepository;
    private final String name;
    private final String pass;

    @Autowired
    public DatabaseStarting(@Value("${spm.root.name}") String name,
                            @Value("${spm.root.pass}") String pass, UserRepository userRepository) {
        this.name = name;
        this.pass = pass;
        this.userRepository = userRepository;
    }

    @Profile("prod")
    @PostConstruct
    void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Root -------------");
        if (this.userRepository.findByName(this.name).isEmpty()) {
            User user = User.builder().name(this.name).password(this.pass).age(21).mobile("111111111").build();
            this.userRepository.create(user);
            LogManager.getLogger(this.getClass()).warn("------- Created Root -------------");
        }
    }
}
