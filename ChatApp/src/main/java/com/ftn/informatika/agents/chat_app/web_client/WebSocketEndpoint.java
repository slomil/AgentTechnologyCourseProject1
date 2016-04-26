package com.ftn.informatika.agents.chat_app.web_client;

import com.ftn.informatika.agents.chat_app.users.users_app.MessageObjectsDbLocal;
import com.ftn.informatika.agents.chat_app.users.users_app.UserAppJmsLocal;
import com.ftn.informatika.agents.chat_app.users.users_app.UserAppRequester;
import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.jms_messages.JmsMessage;
import com.ftn.informatika.agents.model.User;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author - Srđan Milaković
 */
@ServerEndpoint("/data")
@Singleton
public class WebSocketEndpoint {

    @EJB
    private SessionsDbLocal sessionsDbBean;
    @EJB
    private UserAppJmsLocal userAppJmsBean;
    @EJB
    private MessageObjectsDbLocal messageObjectsDbBean;
    @EJB
    private ServerManagementLocal serverManagementBean;

    @OnMessage
    public void onMessage(String message, Session session) {
        if (!session.isOpen()) {
            return;
        }

        try {
            System.out.println("WebSocketEndpoint receiver message:" + message);

            WebsocketPacket websocketPacket = new Gson().fromJson(message, WebsocketPacket.class);
            switch (websocketPacket.getType()) {
                case WebsocketPacket.LOGIN:
                    handleLoginMessage(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.REGISTER:
                    handleLoginMessage(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.LOGOUT:
                    handleLoginMessage(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.MESSAGE:
                    handleLoginMessage(websocketPacket.getPayload(), session);
                    break;
                case WebsocketPacket.USERS:
                    handleLoginMessage(websocketPacket.getPayload(), session);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        if (!sessionsDbBean.containsSession(session)) {
            sessionsDbBean.addSession(session);
            System.out.println("Added session with ID " + session.getId());
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessionsDbBean.removeSession(session);
        System.out.println("Removed session with ID " + session.getId());
    }

    @OnError
    public void onError(Throwable error) {
    }

    private void handleLoginMessage(String payload, Session session) throws IOException {
        User user = new Gson().fromJson(payload, User.class);
        try {
            if (serverManagementBean.isMaster()) {
                JmsMessage jmsMessage = userAppJmsBean.login(user.getUsername(), user.getPassword(), serverManagementBean.getHost());
                messageObjectsDbBean.addMessage(jmsMessage.getUuid(), session);
            } else {
                UserAppRequester.login(serverManagementBean.getMasterAddress(), user.getUsername(), user.getPassword(), serverManagementBean.getHost());
                session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.REMOVED_USER, null, true)));
                // TODO: Add notifier
            }
        } catch (Exception e) {
            session.getBasicRemote().sendText(new Gson().toJson(new WebsocketPacket(WebsocketPacket.REMOVED_USER, e.getClass().getSimpleName(), false)));
        }

    }

    private void handleRegisterMessage(String payload, Session session) {

    }

    private void handleLogoutMessage(String payload, Session session) {

    }

    private void handleMessageMessage(String payload, Session session) {

    }

    private void handleUsersMessage(String payload, Session session) {

    }
}
