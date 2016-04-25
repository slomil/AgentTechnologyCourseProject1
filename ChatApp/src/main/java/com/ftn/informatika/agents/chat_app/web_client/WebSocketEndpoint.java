package com.ftn.informatika.agents.chat_app.web_client;

import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
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

    private void handleLoginMessage(String payload, Session session) {

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
