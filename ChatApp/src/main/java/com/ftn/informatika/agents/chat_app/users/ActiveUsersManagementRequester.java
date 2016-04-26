package com.ftn.informatika.agents.chat_app.users;

import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.ActiveUsersManagementEndpoint;
import com.ftn.informatika.agents.rest_endpoints.ClusterManagementEndpoint;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * @author - Srđan Milaković
 */
public class ActiveUsersManagementRequester {
    private static final String ACTIVE_USERS_MANAGEMENT_URL = "http://%s/chat_app/api/";

    public static void addUser(String destinationAddress, User user) {
        createEndpoint(destinationAddress).addUser(user);
    }

    public static void removeUser(String destinationAddress, User user) {
        createEndpoint(destinationAddress).removeUser(user);
    }

    private static ActiveUsersManagementEndpoint createEndpoint(String destinationAddress) {
        String url = String.format(ACTIVE_USERS_MANAGEMENT_URL, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        return target.proxy(ActiveUsersManagementEndpoint.class);
    }
}
