package org.stafloker.console.commands.userCommands;

import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.services.UserService;

public class Login implements Command {
    private static final String VALUE = "login";
    private static final String PARAMETER_HELP = "<username>;<password>";
    private static final String COMMENT_HELP = "Login to the system";

    private final UserService userService;
    private final View view;
    private final Session session;

    public Login(UserService userService, View view, Session session) {
        this.userService = userService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        if (values.length != 2) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        User user = this.userService.login(values[0], values[1]);
        this.session.setLoggedUser(user);
        this.view.show("Hello, " + user.getName());
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
