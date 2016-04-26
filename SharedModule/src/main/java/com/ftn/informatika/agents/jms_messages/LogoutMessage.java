package com.ftn.informatika.agents.jms_messages;

import com.ftn.informatika.agents.model.User;

/**
 * @author - Srđan Milaković
 */
public class LogoutMessage extends JmsMessage {
    private User user;
    private User response;

    public LogoutMessage() {
    }

    public LogoutMessage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getResponse() {
        return response;
    }

    public void setResponse(User response) {
        this.response = response;
    }
}
