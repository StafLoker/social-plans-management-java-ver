package org.stafloker.services;

import org.springframework.stereotype.Component;
import org.stafloker.data.models.User;
import org.stafloker.services.exceptions.SecurityAuthorizationException;

import java.util.Optional;

@Component
public class Session {
    private User loggedUser;

    public Session() {
        this.loggedUser = null;
    }

    public void assertLogin() {
        if (Optional.ofNullable(this.loggedUser).isEmpty()) {
            throw new SecurityAuthorizationException("Use command/s ~ login ~ and/or ~ create-user ~");
        }
    }

    public User getSecuredUser() {
        assertLogin();
        return this.loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Optional<User> getLoggedUser() {
        return Optional.ofNullable(loggedUser);
    }
}