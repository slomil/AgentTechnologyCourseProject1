package com.ftn.informatika.agents.chat_app.rest;

import com.ftn.informatika.agents.chat_app.db_beans.ActiveUsersDbLocal;
import com.ftn.informatika.agents.chat_app.db_beans.SessionsDbLocal;
import com.ftn.informatika.agents.chat_app.web_client.WebsocketPacket;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.ActiveUsersManagementEndpoint;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class ActiveUsersManagementREST implements ActiveUsersManagementEndpoint {

    @EJB
    private ActiveUsersDbLocal usersDbBean;
    @EJB
    private SessionsDbLocal sessionsDbBean;


    @Override
    public void addUser(User user) {
        usersDbBean.addUser(user);
        WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.NEW_USER, user, true);
        sessionsDbBean.sendMessage(new Gson().toJson(packet));
    }

    @Override
    public void removeUser(User user) {
        usersDbBean.removeUser(user);
        WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.REMOVED_USER, user, true);
        sessionsDbBean.sendMessage(new Gson().toJson(packet));
    }
}
