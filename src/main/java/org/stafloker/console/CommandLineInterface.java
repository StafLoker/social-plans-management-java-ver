package org.stafloker.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.stafloker.services.Session;

import java.util.*;

@Controller
public class CommandLineInterface {
    private static final String VALUE_HELP = "help";
    private static final String HELP_PARAMETERS_HELP = "";
    private static final String HELP_COMMENT_HELP = "Shows help";

    private static final String VALUE_EXIT = "exit";
    private static final String HELP_PARAMETERS_EXIT = "";
    private static final String HELP_COMMENT_EXIT = "Terminates execution";

    private final Session session;
    private final Map<String, Command> commands;
    private final View view;

    @Autowired
    public CommandLineInterface(View view, Session session, ApplicationContext context) {
        this.view = view;
        this.session = session;
        this.commands = this.loadCommands(context);
    }

    private Map<String, Command> loadCommands(ApplicationContext context) {
        Map<String, Command> loadedCommands = new HashMap<>();
        Map<String, Command> commandBeans = context.getBeansOfType(Command.class);

        for (Command command : commandBeans.values()) {
            loadedCommands.put(command.value(), command);
        }

        return loadedCommands;
    }

    public boolean runCommands() {
        boolean exit = false;
        while (!exit) {
            exit = runCommand();
        }
        return true;
    }

    private boolean runCommand() {
        this.view.showCommand(this.session.getLoggedUser().isPresent() ? "-" + this.session.getLoggedUser().get().getName() : "");
        String[] input = this.view.enterCommand();
        boolean exit = false;

        if (VALUE_HELP.equals(input[0])) {
            this.showHelp();
        } else if (VALUE_EXIT.equals(input[0])) {
            exit = true;
        } else {
            if (this.commands.containsKey(input[0])) {
                this.commands.get(input[0]).execute(Arrays.copyOfRange(input, 1, input.length));
            } else {
                throw new UnsupportedOperationException("Command '" + input[0] + "' does not exist.");
            }
        }

        return exit;
    }

    private void showHelp() {
        this.view.showHelp(this.classifyByType());
    }

    private Map<String, List<String[]>> classifyByType() {
        Map<String, List<String[]>> categorizedCommands = new HashMap<>();
        categorizedCommands.put("user", new ArrayList<>());
        categorizedCommands.put("plan", new ArrayList<>());
        categorizedCommands.put("activity", new ArrayList<>());
        for (Command command : this.commands.values()) {
            if (command.value().contains("user") || command.value().contains("login") || command.value().contains("logout")) {
                categorizedCommands.get("user").add(new String[]{command.value(), command.helpParameters(), command.helpComment()});
            } else if (command.value().contains("plan")) {
                categorizedCommands.get("plan").add(new String[]{command.value(), command.helpParameters(), command.helpComment()});
            } else {
                categorizedCommands.get("activity").add(new String[]{command.value(), command.helpParameters(), command.helpComment()});
            }
        }
        categorizedCommands.put("general", new ArrayList<>());
        categorizedCommands.get("general").add(new String[]{VALUE_HELP, HELP_PARAMETERS_HELP, HELP_COMMENT_HELP});
        categorizedCommands.get("general").add(new String[]{VALUE_EXIT, HELP_PARAMETERS_EXIT, HELP_COMMENT_EXIT});
        return categorizedCommands;
    }
}