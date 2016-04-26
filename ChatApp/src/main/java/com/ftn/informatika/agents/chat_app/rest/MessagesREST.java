package com.ftn.informatika.agents.chat_app.rest;

import com.ftn.informatika.agents.chat_app.db_beans.UserSessionDbLocal;
import com.ftn.informatika.agents.chat_app.web_client.WebsocketPacket;
import com.ftn.informatika.agents.model.Message;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.MessagesEndpoint;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;
import java.io.IOException;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessagesREST implements MessagesEndpoint {
    @EJB
    private UserSessionDbLocal userSessionDbBean;

    @Override
    public void publish(Message message) {
        String msg = new Gson().toJson(new WebsocketPacket(WebsocketPacket.MESSAGE, new Gson().toJson(message), true));
        User to = message.getTo();

        if (to != null) {
            Session session = userSessionDbBean.get(to).getSession();
            try {

                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                System.err.println("Socket exception: " + e.getMessage());
            }
        } else {
            userSessionDbBean.getSessions().forEach(s -> {
                try {
                    s.getBasicRemote().sendText(msg);
                } catch (IOException e) {
                    System.err.println("Socket exception: " + e.getMessage());
                }
            });
        }
    }
}
