package org.stafloker.services.exceptions;

public class SecurityProhibitionException extends RuntimeException {
    private static final String DESCRIPTION = "Security Prohibition Exception. You are not authorized";

    public SecurityProhibitionException(String detail) {
        super(DESCRIPTION + " >>> " + detail);
    }
}
