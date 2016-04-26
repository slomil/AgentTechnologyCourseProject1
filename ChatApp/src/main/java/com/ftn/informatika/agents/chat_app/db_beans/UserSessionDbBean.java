package com.ftn.informatika.agents.chat_app.db_beans;

import com.ftn.informatika.agents.model.User;

import javax.ejb.*;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class UserSessionDbBean implements UserSessionDbLocal {

    private HashMap<String, UserSession> sessionKeyMap = new HashMap<>();
    private HashMap<String, UserSession> userKeyMap = new HashMap<>();

    @Override
    public boolean add(UserSession userSession) {
        String sessionKey = userSession.getSession().getId();
        String userKey = userSession.getUser().getUsername();
        if (sessionKeyMap.containsKey(sessionKey) || userKeyMap.containsKey(userKey)) {
            return false;
        }

        sessionKeyMap.put(sessionKey, userSession);
        userKeyMap.put(userKey, userSession);
        return true;
    }

    @Override
    public boolean remove(User user) {
        String userKey = user.getUsername();
        UserSession userSession = userKeyMap.get(userKey);
        if (userSession == null) {
            return false;
        }

        String sessionKey = userSession.getSession().getId();
        if (!sessionKeyMap.containsKey(sessionKey)) {
            return false;
        }

        userKeyMap.remove(userKey);
        sessionKeyMap.remove(sessionKey);
        return true;
    }

    @Override
    public boolean remove(Session session) {
        String sessionKey = session.getId();
        UserSession userSession = sessionKeyMap.get(sessionKey);
        if (userSession == null) {
            return false;
        }

        String userKey = userSession.getUser().getUsername();
        if (!userKeyMap.containsKey(userKey)) {
            return false;
        }

        userKeyMap.remove(userKey);
        sessionKeyMap.remove(sessionKey);
        return true;
    }

    @Override
    public UserSession get(User user) {
        return userKeyMap.get(user.getUsername());
    }

    @Override
    public UserSession get(Session session) {
        return sessionKeyMap.get(session.getId());
    }

    @Override
    public List<UserSession> getUserSessions() {
        return new ArrayList<>(sessionKeyMap.values());
    }

    @Override
    public List<Session> getSessions() {
        List<Session> list = new ArrayList<>();
        sessionKeyMap.values().forEach(us -> list.add(us.getSession()));
        return list;
    }

    @Override
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        userKeyMap.values().forEach(us -> list.add(us.getUser()));
        return list;
    }

    @Override
    public boolean containsSession(Session session) {
        return sessionKeyMap.containsKey(session.getId());
    }

    @Override
    public boolean containsUser(User user) {
        return userKeyMap.containsKey(user.getUsername());
    }

    @Lock(LockType.READ)
    @Override
    public void sendMessage(String message) {
        sessionKeyMap.values().forEach(us -> {
            try {
                us.getSession().getBasicRemote().sendText(message);
            } catch (IOException e) {
                System.err.println("WebSocket exception. " + e.getMessage());
            }
        });
    }
}
