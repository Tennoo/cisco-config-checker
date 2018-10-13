package com.tobias.cisco.configchecker.exceptions;

public class CorruptTaskFileException extends Exception{

    public CorruptTaskFileException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
