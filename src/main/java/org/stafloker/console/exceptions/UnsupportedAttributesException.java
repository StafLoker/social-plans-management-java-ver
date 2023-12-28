package org.stafloker.console.exceptions;

public class UnsupportedAttributesException extends RuntimeException {

    private static final String DESCRIPTION = "Unsupported Attributes Exception. The entered attributes are incorrect";

    public UnsupportedAttributesException(String detail){
        super(DESCRIPTION + " >>> " + detail);
    }
}
