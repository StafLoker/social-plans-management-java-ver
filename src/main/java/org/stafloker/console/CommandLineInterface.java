package org.stafloker.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class CommandLineInterface {
    public static final String DELIMITER_COLON_OR_RETURN = "[:,\\r\\n]";

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
    public CommandLineInterface(View view, Session session) {
        this.commands = new HashMap<>();
        this.view = view;
        this.session = session;
    }

    public void add(Command command) {
        this.commands.put(command.value(), command);
    }

    public boolean runCommands() {
        Scanner scanner = new Scanner(System.in).useDelimiter(DELIMITER_COLON_OR_RETURN);
        boolean exit = false;
        while (!exit) {
            exit = runCommand(scanner);
        }
        return true;
    }

    private boolean runCommand(Scanner scanner) {
        this.view.showCommand(this.session.getLoggedUser().isPresent() ? "-" + this.session.getLoggedUser().get().getName() : "");
        String commandValue = scanner.next().trim().toLowerCase();
        boolean exit = false;

        if (VALUE_HELP.equals(commandValue)) {
            this.showHelp();
        } else if (VALUE_EXIT.equals(commandValue)) {
            exit = true;
        } else {
            if (this.commands.containsKey(commandValue)) {
                this.commands.get(commandValue).execute(readArguments(scanner));
            } else {
                throw new UnsupportedOperationException("Command '" + commandValue + "' does not exist.");
            }
        }

        return exit;
    }

    private void showHelp() {
        List<String[]> generalCommands = new ArrayList<>();
        generalCommands.add(new String[]{VALUE_HELP, HELP_PARAMETERS_HELP, HELP_COMMENT_HELP});
        generalCommands.add(new String[]{VALUE_EXIT, HELP_PARAMETERS_EXIT, HELP_COMMENT_EXIT});
        this.view.showHelp(this.classifyByType(), generalCommands);
    }

    private Map<String, List<Command>> classifyByType() {
        Map<String, List<Command>> categorizedCommands = new HashMap<>();
        categorizedCommands.put("user", new ArrayList<>());
        categorizedCommands.put("plan", new ArrayList<>());
        categorizedCommands.put("activity", new ArrayList<>());
        for (Command command : this.commands.values()) {
            if (command.value().contains("user") || command.value().contains("login") || command.value().contains("logout")) {
                categorizedCommands.get("user").add(command);
            } else if (command.value().contains("plan")) {
                categorizedCommands.get("plan").add(command);
            } else {
                categorizedCommands.get("activity").add(command);
            }
        }
        return categorizedCommands;
    }

    private String[] readArguments(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (!input.isBlank()) {
            return filterParameterSpaces(input.replaceFirst(":", "").split(";"));
        } else {
            return new String[0];
        }
    }

    private String[] filterParameterSpaces(String[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }
        return values;
    }
}
