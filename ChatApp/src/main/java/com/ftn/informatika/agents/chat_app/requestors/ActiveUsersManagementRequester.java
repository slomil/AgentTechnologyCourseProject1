package com.ftn.informatika.agents.chat_app.requestors;

import com.ftn.informatika.agents.chat_app.util.ApplicationConfig;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.ActiveUsersManagementEndpoint;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * @author - Srđan Milaković
 */
public class ActiveUsersManagementRequester {
    public static void addUser(String destinationAddress, User user) {
        createEndpoint(destinationAddress).addUser(user);
    }

    public static void removeUser(String destinationAddress, User user) {
        createEndpoint(destinationAddress).removeUser(user);
    }

    private static ActiveUsersManagementEndpoint createEndpoint(String destinationAddress) {
        String url = String.format(ApplicationConfig.CHAT_APP_URL, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        return target.proxy(ActiveUsersManagementEndpoint.class);
    }
}
