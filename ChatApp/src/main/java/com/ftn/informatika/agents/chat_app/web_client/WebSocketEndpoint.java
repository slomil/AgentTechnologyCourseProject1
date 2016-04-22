package com.ftn.informatika.agents.chat_app.web_client;

import com.ftn.informatika.agents.chat_app.user_app.UserAppUtilLocal;
import com.google.gson.Gson;
import exception.UsernameExistsException;
import model.User;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author - Srđan Milaković
 */
@ServerEndpoint("/data")
@Singleton
public class WebSocketEndpoint {

    private Set<Session> sessions = new HashSet<>();

    @EJB
    private UserAppUtilLocal userAppUtilBean;

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            if (session.isOpen()) {
                User user = new User();
                System.out.println(user);
                userAppUtilBean.register("a", "b");
                System.out.println("WebSocketEndpoint receiver message:" + message);
                SocketPacket socketPacket = new Gson().fromJson(message, SocketPacket.class);
                session.getBasicRemote().sendText("Hello from " + WebSocketEndpoint.class.getSimpleName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UsernameExistsException e) {
            //e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        if (!sessions.contains(session)) {
            System.out.println("Added session with ID " + session.getId());
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Removed session with ID " + session.getId());
    }

    @OnError
    public void onError(Throwable error) {
    }
}
