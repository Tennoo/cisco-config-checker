package com.ciscoconfigchecker.exceptions;

public class CorruptTaskFileException extends Exception {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public CorruptTaskFileException(String message) {
        super(message);
}
}
