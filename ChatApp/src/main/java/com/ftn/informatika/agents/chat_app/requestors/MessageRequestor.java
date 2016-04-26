package com.ftn.informatika.agents.chat_app.requestors;

import com.ftn.informatika.agents.chat_app.util.ApplicationConfig;
import com.ftn.informatika.agents.model.Message;
import com.ftn.informatika.agents.rest_endpoints.ActiveUsersManagementEndpoint;
import com.ftn.informatika.agents.rest_endpoints.MessagesEndpoint;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * @author - Srđan Milaković
 */
public class MessageRequestor {

    public static void publish(String destinationAddress, Message message) {
        createEndpoint(destinationAddress).publish(message);
    }

    private static MessagesEndpoint createEndpoint(String destinationAddress) {
        String url = String.format(ApplicationConfig.CHAT_APP_URL, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        return target.proxy(MessagesEndpoint.class);
    }
}
