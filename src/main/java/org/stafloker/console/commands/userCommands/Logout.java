package org.stafloker.console.commands.userCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Session;
import org.stafloker.console.Command;

@Controller
public class Logout implements Command {
    private static final String VALUE = "logout";
    private static final String PARAMETER_HELP = "";
    private static final String COMMENT_HELP = "Ends the session";

    private final Session session;

    @Autowired
    public Logout(Session session) {
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.setLoggedUser(null);
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