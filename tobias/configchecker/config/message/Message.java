package com.tobias.configchecker.config.message;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private String message;
    private MessageCode code;

    public Message(String message, MessageCode code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String toString() {
        return "message: " + message + "\n" + "code: " + code;
    }

    public String getMessage(){
        return message;
    }

    public MessageCode getCode() {
        return code;
    }
}
