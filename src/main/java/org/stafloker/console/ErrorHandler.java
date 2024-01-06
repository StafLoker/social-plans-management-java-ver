package org.stafloker.console;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.stafloker.services.exceptions.SecurityProhibitionException;

@Profile({"dev", "prod"})
@Controller
public class ErrorHandler {
    private final CommandLineInterface commandLineInterface;
    private final View view;

    @Autowired
    public ErrorHandler(CommandLineInterface commandLineInterface, View view) {
        this.commandLineInterface = commandLineInterface;
        this.view = view;
        this.view.showWelcome();
    }

    @PostConstruct
    public void errorManager() {
        boolean exit = false;
        while (!exit) {
            try {
                exit = this.commandLineInterface.runCommands();
            } catch (SecurityProhibitionException securityProhibitionException) {
                this.view.showErrorWithComment("Oops...", securityProhibitionException.getMessage());
            } catch (Exception e) {
                this.view.showErrorWithComment("ERROR!", e.getMessage());
            }
        }
        this.view.show("Goodbye!");
    }
}
