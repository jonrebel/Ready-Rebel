package com.jontdev.readyrebrel;

public class MessageModel {
    public static String SENT_BY_ME ="me";
    public static String SENT_BY_REB ="rebel";

    String message;
    String sentBy;

    public MessageModel(String message, String sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
