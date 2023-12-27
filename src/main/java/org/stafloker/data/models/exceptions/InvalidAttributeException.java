package org.stafloker.data.models.exceptions;

public class InvalidAttributeException extends RuntimeException{
    private static final String DESCRIPTION = "Invalid attribute exception. Attribute out of range";

    public InvalidAttributeException(String detail) {
        super(DESCRIPTION + " >>> " + detail);
    }
}