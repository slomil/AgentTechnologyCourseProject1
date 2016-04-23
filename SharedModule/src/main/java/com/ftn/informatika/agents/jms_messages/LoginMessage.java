package com.ftn.informatika.agents.jms_messages;

import com.ftn.informatika.agents.model.Host;

/**
 * @author - Srđan Milaković
 */
public class LoginMessage extends JmsMessage {
    private String username;
    private String password;
    private Host host;

    public LoginMessage() {
    }

    public LoginMessage(String username, String password, Host host) {
        this.username = username;
        this.password = password;
        this.host = host;
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

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
