package com.tobias.configchecker.config.message;

public class Message {

    private String message;
    private MessageCode code;

    public Message(String message, MessageCode code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "message: " + message + "\n" + "code: " + code;
    }

    public MessageCode getCode() {
        return code;
    }
}
