package com.tobias.configchecker.config.message;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private String message;
    private MessageCode code;
    private static List<Message> detailedMessages = new ArrayList<>();
    private static List<Message> briefMessages = new ArrayList<>();

    public Message(String message, MessageCode code) {
        this.message = message;
        this.code = code;
    }

    public static  List<Message> getDetailedMessageByCode(MessageCode code) {
        List<Message> messages = new ArrayList<>();
        for(int i = 0; i < detailedMessages.size(); i++){
            if(detailedMessages.get(i).code == code){
                System.out.println(detailedMessages.get(i).toString());
                messages.add(detailedMessages.get(i));
            }
        }



        return messages;
    }


    public static void addDetailedMessage(Message detailedMessage){
        if(detailedMessage != null){
            detailedMessages.add(detailedMessage);
        }

    }

    public static void addBriefMessage(Message briefMessage){
        if(briefMessage != null){
            briefMessages.add(briefMessage);
        }

    }

    public static List<Message> getDetailedMessages() {
        return detailedMessages;
    }

    public static List<Message> getBriefMessages() {
        return briefMessages;
    }

    @Override
    public String toString() {
        return "message: " + message + "\n" + "code: " + code;
    }

    public MessageCode getCode() {
        return code;
    }
}
