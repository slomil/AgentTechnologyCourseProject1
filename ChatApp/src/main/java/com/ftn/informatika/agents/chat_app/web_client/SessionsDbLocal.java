package com.ftn.informatika.agents.chat_app.web_client;

import javax.websocket.Session;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
public interface SessionsDbLocal {
    void addSession(Session session);
    void removeSession(Session session);
    List<Session> getSessions();
    boolean containsSession(Session session);
    void sendMessage(String message);
}
