package org.stafloker.services.exceptions;

public class SecurityAuthorizationException extends RuntimeException {
    private static final String DESCRIPTION = "Security Authorization Exception. You are not registered";

    public SecurityAuthorizationException(String detail) {
        super(DESCRIPTION + " >>> " + detail);
    }
}
