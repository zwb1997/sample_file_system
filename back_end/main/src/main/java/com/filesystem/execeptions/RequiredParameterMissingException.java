package com.filesystem.execeptions;

public class RequiredParameterMissingException extends RuntimeException {

    public RequiredParameterMissingException() {
        super();
    }

    public RequiredParameterMissingException(String pName) {
        super(String.format("required parameters missing -> [%s]", pName));
    }

}
