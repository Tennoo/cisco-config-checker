package com.ciscoconfigchecker.exceptions;

public class CorruptConfigException extends Exception {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public CorruptConfigException(String message) {
        super(message);
    }
}
