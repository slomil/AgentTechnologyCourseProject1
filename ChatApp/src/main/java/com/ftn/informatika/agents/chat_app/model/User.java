package com.ftn.informatika.agents.chat_app.model;

/**
 * @author - Srđan Milaković
 */
public class User {
    private String username;
    private String password;
    private String host;

    public User() {
    }

    public User(String username, String password) {
        this(username, password, null);
    }

    public User(String username, String password, String host) {
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
