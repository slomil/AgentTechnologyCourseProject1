package com.ftn.informatika.agents.jms_messages;

import com.ftn.informatika.agents.model.User;

/**
 * @author - Srđan Milaković
 */
public class RegisterMessage extends JmsMessage {
    private String username;
    private String password;
    private User response;

    public RegisterMessage() {
    }

    public RegisterMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getResponse() {
        return response;
    }

    public void setResponse(User response) {
        this.response = response;
    }
}
