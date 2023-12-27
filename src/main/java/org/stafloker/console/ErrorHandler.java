package org.stafloker.console;

import org.stafloker.services.exceptions.SecurityProhibitionException;

public class ErrorHandler {
    private final CommandLineInterface commandLineInterface;
    private final View view;

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
