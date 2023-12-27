package org.stafloker.console;

public interface Command {
    void execute(String[] values);

    String value();

    String helpParameters();

    String helpComment();
}
