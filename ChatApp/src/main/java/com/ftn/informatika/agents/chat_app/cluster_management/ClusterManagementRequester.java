package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.rest_endpoints.ClusterManagementEndpoint;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class ClusterManagementRequester {
    private static final String CHAT_APP_URL = "http://%s/chat_app/api/";

    public static List<Host> register(String destinationAddress, String address, String alias)
            throws AliasExistsException {
        return createEndpoint(destinationAddress).register(address, alias);
    }

    public static void unregister(String destinationAddress, Host host) throws HostNotExistsException {
        createEndpoint(destinationAddress).unregister(host);
    }

    private static ClusterManagementEndpoint createEndpoint(String destinationAddress) {
        String url = String.format(CHAT_APP_URL, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        return target.proxy(ClusterManagementEndpoint.class);
    }
}
