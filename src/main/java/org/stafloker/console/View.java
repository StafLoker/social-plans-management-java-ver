package org.stafloker.console;

import org.springframework.stereotype.Controller;
import org.stafloker.data.models.User;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.Plan;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class View {
    public static final String COMMAND = "spm";

    public static final String RESET = "\u001B[0m";
    public static final String ORANGE = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String SALMON = "\u001B[38;5;209m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";


    public void showWelcome() {
        System.out.println(ORANGE + "Welcome, I'm Minutca, your universal assistant. Thank you for choosing our application, " + PURPLE + "Social Plans Management"
                + ORANGE + ". To check available commands, type: " + BLUE + "help" + ORANGE + "." + RESET);
    }

    public void showCommand(String message) {
        System.out.print(COMMAND + message + "> ");
    }

    public void showHelp(Map<String, List<Command>> commandCategories, List<String[]> generalCommands) {
        System.out.println(BLUE + "   " + "Sure, I'll help you. Here's a list of possible commands. Just a note, " +
                "mandatory parameters <parameter>, optional <<parameter>>." + RESET);
        System.out.println(BLUE + "   " + "Just in case, command format (if no parameters, the ':' can be omitted) -->> " +
                GREEN + "<value>: <<parameter-1>>; ... ; <<parameter-N>>" + RESET);
        this.show("Commands related to User:");
        commandCategories.get("user").forEach(com -> this.showCommandHelp(com.value(), com.helpParameters(), com.helpComment()));
        this.show("Commands related to Plan:");
        commandCategories.get("plan").forEach(com -> this.showCommandHelp(com.value(), com.helpParameters(), com.helpComment()));
        this.show("Commands related to Activity:");
        commandCategories.get("activity").forEach(com -> this.showCommandHelp(com.value(), com.helpParameters(), com.helpComment()));
        this.show("General Commands:");
        generalCommands.forEach(com -> this.showCommandHelp(com[0], com[1], com[2]));
    }

    private void showCommandHelp(String command, String helpParameters, String help) {
        System.out.println(CYAN + "      - " + command + ORANGE + ":" + helpParameters + CYAN + " " + help + "." + RESET);
    }

    public void showErrorWithComment(String errorMessage, String comment) {
        System.out.println(RED + "  >>> " + errorMessage + " >>> " + SALMON + comment + RESET);
    }

    public void showResult(String message) {
        System.out.println(CYAN + "   - " + message + RESET);
    }

    public void show(String message) {
        System.out.println(BLUE + "   " + message + RESET);
    }

    public void showUser(User user) {
        System.out.println(CYAN + "  " + "<~ User ~> \n" +
                "       ID: " + user.getId() + "\n" +
                "       Name: " + ORANGE + user.getName() + CYAN + "\n" +
                "       Password: " + PURPLE + user.getPassword() + CYAN + "\n" +
                "       Age: " + user.getAge() + "\n" +
                "       Mobile: " + user.getMobile() +
                RESET
        );
    }

    public void showPlan(Plan plan) {
        System.out.println(CYAN + "  " + "<~ Plan ~> \n" +
                "       ID: " + ORANGE + plan.getId() + CYAN + "\n" +
                "       Name: " + plan.getName() + "\n" +
                "       Owner: " + plan.getOwner().getName() + "\n" +
                "       Date: " + plan.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm")) + "\n" +
                "       Meeting Place: " + plan.getMeetingPlace() + "\n" +
                "       Capacity: " + (plan.getCapacity() == null ? "No limit" : plan.getCapacity()) + "\n" +
                "       Available spots: " + (plan.availableSpots() == null ? "No limit" : plan.availableSpots()) + "\n" +
                "       Activities: " + plan.getActivities().stream().map(Activity::getName).toList() + "\n" +
                "       Subscribers: " + plan.getSubscribers().stream().map(User::getName).toList() +
                RESET
        );
    }

    public void showPlans(Plan plan) {
        System.out.println(CYAN + "   - " + "Plan ~> " +
                "ID: " + ORANGE + plan.getId() + CYAN +
                " | Name: " + BLUE + plan.getName() + CYAN +
                " | Owner: " + plan.getOwner().getName() + CYAN +
                " | Date: " + BLUE + plan.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm")) + CYAN +
                " | Meeting Place: " + BLUE + plan.getMeetingPlace() + CYAN +
                " | Capacity: " + (plan.getCapacity() == null ? "No limit" : plan.getCapacity()) +
                " | Available spots: " + BLUE + (plan.availableSpots() == null ? "No limit" : plan.availableSpots()) + "\n" + CYAN +
                "       Activities: " + plan.getActivities().stream().map(Activity::getName).toList() + "\n" +
                "       Subscribers: " + plan.getSubscribers().stream().map(User::getName).toList() +
                RESET
        );
    }

    public void showActivity(Activity activity) {
        System.out.println(CYAN + "  " + "<~ Activity ~> \n" +
                "       ID: " + ORANGE + activity.getId() + CYAN + "\n" +
                "       Name: " + activity.getName() + "\n" +
                "       Description: " + activity.getDescription() + "\n" +
                "       Type: " + activity.getClass().getSimpleName() + "\n" +
                "       Duration: " + activity.getDuration() / 60 + " hour/s " + activity.getDuration() % 60 + " minute/s" + "\n" +
                "       Capacity: " + (activity.getCapacity() == null ? "No limit" : activity.getCapacity()) + "\n" +
                "       Price: " + activity.getPrice() + " €" +
                RESET
        );
    }
}
