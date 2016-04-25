package com.ftn.informatika.agents.chat_app.users;

import com.ftn.informatika.agents.chat_app.cluster_management.HostsDbLocal;
import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.chat_app.web_client.SessionsDbLocal;
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
    private HostsDbLocal hostsDbBean;
    @EJB
    private UsersDbLocal usersDbBean;
    @EJB
    private SessionsDbLocal sessionsDbBean;
    @EJB
    private ServerManagementLocal serverManagementBean;

    @Override
    public void addUser(User user) {
        usersDbBean.addUser(user);
        hostsDbBean.getHosts().forEach(h -> {
            if (!serverManagementBean.getLocalAddress().equals(h.getAddress())) {
                ActiveUsersManagementRequester.addUser(h.getAddress(), user);
            }
        });

        WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.NEW_USER, user, true);
        sessionsDbBean.sendMessage(new Gson().toJson(packet));
    }

    @Override
    public void removeUser(User user) {
        usersDbBean.removeUser(user);
        hostsDbBean.getHosts().forEach(h -> {
            if (!serverManagementBean.getLocalAddress().equals(h.getAddress())) {
                ActiveUsersManagementRequester.removeUser(h.getAddress(), user);
            }
        });

        WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.REMOVED_USER, user, true);
        sessionsDbBean.sendMessage(new Gson().toJson(packet));
    }
}
