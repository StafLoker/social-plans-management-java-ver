package org.stafloker.data.repositories.seeder;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import org.stafloker.data.models.User;
import org.stafloker.data.repositories.UserRepository;

@Repository
@Profile({"dev", "test"})
public class UserSeederDev {

    private final DatabaseStarting databaseStarting;
    private final UserRepository userRepository;

    @Autowired
    public UserSeederDev(DatabaseStarting databaseStarting, UserRepository userRepository) {
        this.databaseStarting = databaseStarting;
        this.userRepository = userRepository;
    }

    public void seedDataBase() {
        User[] users = {
                User.builder().name("User1").password("123").age(25).mobile("222222222").build(),
                User.builder().name("User2").password("123").age(26).mobile("333333333").build(),
                User.builder().name("User3").password("123").age(64).mobile("444444444").build(),
                User.builder().name("User4").password("123").age(65).mobile("555555555").build(),
                User.builder().name("User5").password("123").age(70).mobile("666666666").build()
        };
        for (User user : users) {
            this.userRepository.create(user);
        }
    }

    public void deleteAllAndInitialize() {
        this.userRepository.deleteAll();
        LogManager.getLogger(this.getClass()).warn("------- Users deleted All -------");
        this.databaseStarting.initialize();
    }
}
