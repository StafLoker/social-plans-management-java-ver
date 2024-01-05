package org.stafloker.data.daos.seeders;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import org.stafloker.data.models.User;
import org.stafloker.data.daos.UserRepository;

import java.util.Arrays;

@Repository
@Profile("dev")
public class UserSeederDev {

    private final DatabaseStarting databaseStarting;
    private final UserRepository userRepository;
    private final String pass;

    @Autowired
    public UserSeederDev(DatabaseStarting databaseStarting, UserRepository userRepository, @Value("${spm.user.pass}") String pass) {
        this.databaseStarting = databaseStarting;
        this.userRepository = userRepository;
        this.pass = pass;
    }

    public void seedDataBase() {
        User[] users = {
                User.builder().name("User1").password(this.pass).age(25).mobile("222222222").build(),
                User.builder().name("User2").password(this.pass).age(26).mobile("333333333").build(),
                User.builder().name("User3").password(this.pass).age(64).mobile("444444444").build(),
                User.builder().name("User4").password(this.pass).age(65).mobile("555555555").build(),
                User.builder().name("User5").password(this.pass).age(70).mobile("666666666").build()
        };
        this.userRepository.saveAll(Arrays.asList(users));
        LogManager.getLogger(this.getClass()).warn("------- Users Loaded -------");
    }

    public void deleteAllAndInitialize() {
        this.userRepository.deleteAll();
        LogManager.getLogger(this.getClass()).warn("------- Users Deleted All -------");
        this.databaseStarting.initialize();
    }
}
