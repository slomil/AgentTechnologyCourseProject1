package com.ftn.informatika.agents.chat_app.web_client;

/**
 * @author - Srđan Milaković
 */
public class ResponsePacket {
    private boolean success;
    private String message;

    public ResponsePacket() {
    }

    public ResponsePacket(boolean success, String message) {
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
