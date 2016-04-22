package com.ftn.informatika.agents.chat_app.web_client;

import com.google.gson.Gson;

/**
 * @author - Srđan Milaković
 */
public class RequestPacket {
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String LOGOUT = "logout";
    public static final String MESSAGE = "message";

    private String type;
    private String payload;

    public RequestPacket() {
    }

    public RequestPacket(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public RequestPacket(String type, Object payload) {
        this.type = type;
        this.payload = new Gson().toJson(payload);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
