package com.ftn.informatika.agents.chat_app.web_client;

import com.ftn.informatika.agents.chat_app.db_beans.HostsDbLocal;
import com.ftn.informatika.agents.chat_app.db_beans.UserSessionDbLocal;
import com.ftn.informatika.agents.chat_app.requesters.ActiveUsersManagementRequester;
import com.ftn.informatika.agents.chat_app.db_beans.ActiveUsersDbLocal;
import com.ftn.informatika.agents.chat_app.db_beans.MessageSessionDbLocal;
import com.ftn.informatika.agents.chat_app.jms_user_queue.UserAppJmsLocal;
import com.ftn.informatika.agents.chat_app.requesters.MessageRequester;
import com.ftn.informatika.agents.chat_app.requesters.UserAppRequester;
import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.jms_messages.JmsMessage;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.Message;
import com.ftn.informatika.agents.model.User;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.jms.JMSException;
import javax.jws.soap.SOAPBinding;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@ServerEndpoint("/data")
@Singleton
public class WebSocketEndpoint {

    @EJB
    private UserSessionDbLocal userSessionDbBean;
    @EJB
    private UserAppJmsLocal userAppJmsBean;
    @EJB
    private HostsDbLocal hostsDbBean;
    @EJB
    private ActiveUsersDbLocal activeUsersDbBean;
    @EJB
    private MessageSessionDbLocal messageObjectsDbBean;
    @EJB
    private ServerManagementLocal serverManagementBean;

    @OnMessage
    public void onMessage(String message, Session session) {
        if (!session.isOpen()) {
            return;
        }

        try {
            System.out.println("WebSocketEndpoint receiver message:" + message);
//            new WebsocketPacket();
            WebsocketPacket websocketPacket = new Gson().fromJson(message, WebsocketPacket.class);
            switch (websocketPacket.getType()) {
                case WebsocketPacket.LOGIN:
                    handleLoginPayload(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.REGISTER:
                    handleRegisterPayload(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.LOGOUT:
                    handleLogoutPayload(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.MESSAGE:
                    handleMessagePayload(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.USERS:
                    handleUsersPayload(websocketPacket.getPayload(), session);
                    break;
            }
        } catch (IOException e) {
            System.err.println("WebSocket Exception: " + e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New session with ID " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Removed session with ID " + session.getId());
        if (userSessionDbBean.containsSession(session)) {
            User user = userSessionDbBean.get(session).getUser();
            if (serverManagementBean.isMaster()) {
                try {
                    userAppJmsBean.logout(user);
                } catch (JMSException e) {
                    System.err.println("JmsException: " + e.getMessage());
                }
            } else {
                UserAppRequester.logout(serverManagementBean.getMasterAddress(), user);
                hostsDbBean.getHosts().forEach(h -> ActiveUsersManagementRequester.removeUser(h.getAddress(), user));
                userSessionDbBean.remove(user);
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        System.err.println("onError: " + error.getMessage());
    }

    private void handleLoginPayload(String payload, Session session) throws IOException {
        User user = new Gson().fromJson(payload, User.class);
        try {
            if (serverManagementBean.isMaster()) {
                JmsMessage jmsMessage = userAppJmsBean.login(user.getUsername(), user.getPassword(), serverManagementBean.getHost());
                messageObjectsDbBean.addMessage(jmsMessage.getUuid(), session);
            } else {
                user.setHost(serverManagementBean.getHost());
                UserAppRequester.login(serverManagementBean.getMasterAddress(), user.getUsername(), user.getPassword(), serverManagementBean.getHost());
                session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.LOGIN, user, true)));
                hostsDbBean.getHosts().forEach(h -> ActiveUsersManagementRequester.addUser(h.getAddress(), user));
                if (!userSessionDbBean.add(new UserSessionDbLocal.UserSession(session, user))) {
                    throw new Exception("Server error, can not add user for specified session.");
                }
            }
        } catch (Exception e) {
            session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.ERROR, e.getClass().getSimpleName(), false)));
        }
    }

    private void handleRegisterPayload(String payload, Session session) throws IOException {
        User user = new Gson().fromJson(payload, User.class);
        try {
            if (serverManagementBean.isMaster()) {
                JmsMessage jmsMessage = userAppJmsBean.register(user.getUsername(), user.getPassword());
                messageObjectsDbBean.addMessage(jmsMessage.getUuid(), session);
            } else {
                UserAppRequester.register(serverManagementBean.getMasterAddress(), user.getUsername(), user.getPassword());
                session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.REGISTER, null, true)));
            }
        } catch (Exception e) {
            session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.ERROR, e.getClass().getSimpleName(), false)));
        }
    }

    private void handleLogoutPayload(String payload, Session session) throws IOException {
        User user = new Gson().fromJson(payload, User.class);
        try {
            if (serverManagementBean.isMaster()) {
                JmsMessage jmsMessage = userAppJmsBean.logout(user);
                messageObjectsDbBean.addMessage(jmsMessage.getUuid(), session);
            } else {
                UserAppRequester.logout(serverManagementBean.getMasterAddress(), user);
                session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.LOGOUT, null, true)));
                hostsDbBean.getHosts().forEach(h -> ActiveUsersManagementRequester.removeUser(h.getAddress(), user));
                userSessionDbBean.remove(user);
            }
        } catch (Exception e) {
            session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.ERROR, e.getClass().getSimpleName(), false)));
        }
    }

    private void handleMessagePayload(String payload, Session session) {
        Message message = new Gson().fromJson(payload, Message.class);
        User to = message.getTo();

        if (to != null) {
            to = activeUsersDbBean.getUser(to);
            Host host = to.getHost();
            MessageRequester.publish(host.getAddress(), message);
        } else {
            hostsDbBean.getHosts().forEach(h -> MessageRequester.publish(h.getAddress(), message));
        }
    }

    private void handleUsersPayload(String payload, Session session) throws IOException {
        List<User> users = activeUsersDbBean.getUsers();
        session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.USERS, users, true)));
    }
}
