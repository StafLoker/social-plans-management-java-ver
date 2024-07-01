package org.stafloker.console;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Controller
public class ViewConsole implements View {
    public static final String DELIMITER_COLON_OR_RETURN = "[:,\\r\\n]";
    public static final String DATE_FORMAT = "dd-MM-yyyy HH.mm";

    public static final String COMMAND = "spm";

    public static final String RESET = "\u001B[0m";
    public static final String ORANGE = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String SALMON = "\u001B[38;5;209m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";

    private final Scanner scanner;

    public ViewConsole() {
        this.scanner = new Scanner(System.in).useDelimiter(DELIMITER_COLON_OR_RETURN);
    }

    @Override
    public String[] enterCommand() {
        String command = this.scanner.next().trim().toLowerCase();
        String[] arguments = readArguments();
        if (arguments != null) {
            String[] input = new String[1 + arguments.length];
            input[0] = command;
            System.arraycopy(arguments, 0, input, 1, arguments.length);
            return input;
        } else {
            return new String[]{command};
        }
    }

    private String[] readArguments() {
        String input = this.scanner.nextLine().trim();
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


    @Override
    public void showWelcome() {
        System.out.println(ORANGE + "Welcome, I'm Minutca, your universal assistant. Thank you for choosing our application, " + PURPLE + "Social Plans Management"
                + ORANGE + ". To check available commands, type: " + BLUE + "help" + ORANGE + "." + RESET);
    }

    @Override
    public void showCommand(String message) {
        System.out.print(COMMAND + message + "> ");
    }

    @Override
    public void showHelp(Map<String, List<String[]>> commandCategories) {
        System.out.println(BLUE + "   " + "Sure, I'll help you. Here's a list of possible commands. Just a note, " +
                "mandatory parameters <parameter>, optional <<parameter>>." + RESET);
        System.out.println(BLUE + "   " + "Just in case, command format (if no parameters, the ':' can be omitted) -->> " +
                GREEN + "<value>: <<parameter-1>>; ... ; <<parameter-N>>" + RESET);
        this.show("Commands related to User:");
        commandCategories.get("user").forEach(com -> this.showCommandHelp(com[0], com[1], com[2]));
        this.show("Commands related to Plan:");
        commandCategories.get("plan").forEach(com -> this.showCommandHelp(com[0], com[1], com[2]));
        this.show("Commands related to Activity:");
        commandCategories.get("activity").forEach(com -> this.showCommandHelp(com[0], com[1], com[2]));
        this.show("General Commands:");
        commandCategories.get("general").forEach(com -> this.showCommandHelp(com[0], com[1], com[2]));
    }

    private void showCommandHelp(String command, String helpParameters, String help) {
        System.out.println(CYAN + "      - " + command + ORANGE + ":" + helpParameters + CYAN + " " + help + "." + RESET);
    }

    @Override
    public void showErrorWithComment(String errorMessage, String comment) {
        System.out.println(RED + "  >>> " + errorMessage + " >>> " + SALMON + comment + RESET);
    }

    @Override
    public void showResult(String message) {
        System.out.println(CYAN + "   - " + message + RESET);
    }

    @Override
    public void show(String message) {
        System.out.println(BLUE + "   " + message + RESET);
    }

    @Override
    public void showUser(Long id, String username, String password, Integer age, String mobile) {
        System.out.println(CYAN + "  " + "<~ User ~> \n" +
                "       ID: " + id + "\n" +
                "       Name: " + ORANGE + username + CYAN + "\n" +
                "       Password: " + PURPLE + password + CYAN + "\n" +
                "       Age: " + age + "\n" +
                "       Mobile: " + mobile +
                RESET
        );
    }

    @Override
    public void showPlan(Long id, String name, String ownerName, LocalDateTime date, String meetingPlace, Integer capacity, Integer availableSpots, List<String> activitiesNames, List<String> subscribersNames) {
        System.out.println(CYAN + "  " + "<~ Plan ~> \n" +
                "       ID: " + ORANGE + id + CYAN + "\n" +
                "       Name: " + name + "\n" +
                "       Owner: " + ownerName + "\n" +
                "       Date: " + date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + "\n" +
                "       Meeting Place: " + meetingPlace + "\n" +
                "       Capacity: " + (capacity == null ? "No limit" : capacity) + "\n" +
                "       Available spots: " + (availableSpots == null ? "No limit" : availableSpots) + "\n" +
                "       Activities: " + activitiesNames + "\n" +
                "       Subscribers: " + subscribersNames +
                RESET
        );
    }

    @Override
    public void showPlanForList(Long id, String name, String ownerName, LocalDateTime date, String meetingPlace, Integer capacity, Integer availableSpots, List<String> activitiesNames, List<String> subscribersNames) {
        System.out.println(CYAN + "   - " + "Plan ~> " +
                "ID: " + ORANGE + id + CYAN +
                " | Name: " + BLUE + name + CYAN +
                " | Owner: " + ownerName + CYAN +
                " | Date: " + BLUE + date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + CYAN +
                " | Meeting Place: " + BLUE + meetingPlace + CYAN +
                " | Capacity: " + (capacity == null ? "No limit" : capacity) +
                " | Available spots: " + BLUE + (availableSpots == null ? "No limit" : availableSpots) + "\n" + CYAN +
                "       Activities: " + activitiesNames + "\n" +
                "       Subscribers: " + subscribersNames +
                RESET
        );
    }

    @Override
    public void showActivity(Long id, String name, String description, String type, Integer duration, Integer capacity, Double price) {
        System.out.println(CYAN + "  " + "<~ Activity ~> \n" +
                "       ID: " + ORANGE + id + CYAN + "\n" +
                "       Name: " + name + "\n" +
                "       Description: " + description + "\n" +
                "       Type: " + type + "\n" +
                "       Duration: " + duration / 60 + " hour/s " + duration % 60 + " minute/s" + "\n" +
                "       Capacity: " + (capacity == null ? "No limit" : capacity) + "\n" +
                "       Price: " + (price == 0.0 ? "free" : price + " €") +
                RESET
        );
    }
}
