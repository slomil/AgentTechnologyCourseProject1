package com.ftn.informatika.agents.chat_app.db_beans;

import com.ftn.informatika.agents.model.User;

import javax.websocket.Session;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
public interface UserSessionDbLocal {

    class UserSession {
        private Session session;
        private User user;

        public UserSession() {
            this(null, null);
        }

        public UserSession(Session session, User user) {
            this.session = session;
            this.user = user;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    void add(UserSession userSession);
    void remove(User user);
    void remove(Session session);

    List<UserSession> getUserSessions();
    List<Session> getSessions();
    List<User> getUsers();

    boolean containsSession(Session session);
    boolean containsUser(User user);
    void sendMessage(String message);
}
