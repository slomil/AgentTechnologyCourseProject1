package com.ftn.informatika.agents.jms_messages;

import com.ftn.informatika.agents.model.User;

import java.util.List;
import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
public class GetActiveUsersMessage extends JmsMessage {
    private List<User> response;

    public List<User> getResponse() {
        return response;
    }

    public void setResponse(List<User> response) {
        this.response = response;
    }
}
