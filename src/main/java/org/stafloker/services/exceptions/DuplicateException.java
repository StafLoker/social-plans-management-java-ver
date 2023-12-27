package org.stafloker.services.exceptions;

public class DuplicateException extends RuntimeException {
    private static final String DESCRIPTION = "Duplicate Exception. The attribute must be unique";

    public DuplicateException(String detail) {
        super(DESCRIPTION + " >>> " + detail);
    }
}
