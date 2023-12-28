package org.stafloker.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stafloker.services.exceptions.SecurityProhibitionException;

@Component
public class ErrorHandler {
    private final CommandLineInterface commandLineInterface;
    private final View view;

    @Autowired
    public ErrorHandler(CommandLineInterface commandLineInterface, View view) {
        this.commandLineInterface = commandLineInterface;
        this.view = view;
        this.view.showWelcome();
    }

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
