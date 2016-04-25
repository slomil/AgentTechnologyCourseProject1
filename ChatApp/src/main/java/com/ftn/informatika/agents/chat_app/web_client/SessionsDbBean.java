package com.ftn.informatika.agents.chat_app.web_client;

import javax.ejb.*;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class SessionsDbBean implements SessionsDbLocal {

    private HashMap<String, Session> sessions = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeSession(Session session) {
        sessions.remove(session.getId());
    }

    @Lock(LockType.READ)
    @Override
    public List<Session> getSessions() {
        return new ArrayList<>(sessions.values());
    }

    @Lock(LockType.READ)
    @Override
    public boolean containsSession(Session session) {
        return sessions.containsKey(session.getId());
    }

    @Lock(LockType.READ)
    @Override
    public void sendMessage(String message) {
        sessions.values().forEach(s -> {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                System.err.println("WebSocket exception. " + e.getMessage());
            }
        });
    }
}
