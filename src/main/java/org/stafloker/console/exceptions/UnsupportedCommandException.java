package org.stafloker.console.exceptions;

public class UnsupportedCommandException extends RuntimeException {

    private static final String DESCRIPTION = "Unsupported Command Exception. The entered command does not exist";

    public UnsupportedCommandException(String detail) {
        super(DESCRIPTION + " >>> " + detail);
    }
}
