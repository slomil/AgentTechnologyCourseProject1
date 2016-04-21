package com.ftn.informatika.agents.chat_app.web_client;

/**
 * @author - Srđan Milaković
 */
public class Response {
    private boolean success;
    private String message;

    public Response() {
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
