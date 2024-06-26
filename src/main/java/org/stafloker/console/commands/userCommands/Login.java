package org.stafloker.console.commands.userCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.console.exceptions.UnsupportedCommandException;
import org.stafloker.services.Session;
import org.stafloker.services.UserService;

@Controller
public class Login implements Command {
    private static final String VALUE = "login";
    private static final String PARAMETER_HELP = "<username>;<password>";
    private static final String COMMENT_HELP = "Login to the system";

    private final UserService userService;
    private final View view;
    private final Session session;

    @Autowired
    public Login(UserService userService, View view, Session session) {
        this.userService = userService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        if (this.session.getLoggedUser().isPresent()) {
            throw new UnsupportedCommandException("You are already logged in.");
        }
        if (values.length != 2) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        this.userService.logIn(values[0], values[1]);
        this.view.show("Hello, " + this.session.getSecuredUser().getName());
    }

    @Override
    public String value() {
        return VALUE;
    }

    @Override
    public String helpParameters() {
        return PARAMETER_HELP;
    }

    @Override
    public String helpComment() {
        return COMMENT_HELP;
    }
}
