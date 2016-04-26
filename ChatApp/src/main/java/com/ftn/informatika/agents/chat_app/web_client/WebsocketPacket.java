package com.ftn.informatika.agents.chat_app.web_client;

import com.google.gson.Gson;

/**
 * @author - Srđan Milaković
 */
public class WebsocketPacket {
    public static final String ERROR = "error";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String LOGOUT = "logout";
    public static final String MESSAGE = "message";
    public static final String USERS = "users";
    public static final String NEW_USER = "new_user";
    public static final String REMOVED_USER = "removed_user";

    private String type;
    private String payload;
    private Boolean success;

    public WebsocketPacket() {
    }

    public WebsocketPacket(String type, String payload, Boolean success) {
        this.type = type;
        this.payload = payload;
        this.success = success;
    }

    public WebsocketPacket(String type, Object payload, Boolean success) {
        this(type, new Gson().toJson(payload), success);
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
