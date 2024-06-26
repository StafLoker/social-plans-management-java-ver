package org.stafloker.console.commands.userCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.services.UserService;

@Controller
public class CreateUser implements Command {
    private static final String VALUE = "create-user";
    private static final String PARAMETER_HELP = "<name>;<password>;<age>;<mobile>";
    private static final String COMMENT_HELP = "Register new user";

    private final UserService userService;
    private final View view;

    @Autowired
    public CreateUser(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }

    @Override
    public void execute(String[] values) {
        if (values.length != 4) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        User createdUser = this.userService.create(values[0], values[1], Integer.parseInt(values[2]), values[3].replace(" ", ""));
        this.view.showUser(createdUser.getId(), createdUser.getName(), createdUser.getPassword(), createdUser.getAge(), createdUser.getMobile());
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
