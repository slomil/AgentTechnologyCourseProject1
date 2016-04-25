package com.ftn.informatika.agents.chat_app.cluster_management.util;

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
public class RestRequester {
    private static final String BASIC_URL_FORMAT = "http://%s/chat_app/api/";

    public static List<Host> register(String destinationAddress, String address, String alias)
            throws AliasExistsException {
        String url = String.format(BASIC_URL_FORMAT, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        ClusterManagementEndpoint endpoint = target.proxy(ClusterManagementEndpoint.class);
        return endpoint.register(address, alias);
    }

    public static void unregister(String destinationAddress, Host host) throws HostNotExistsException {
        String url = String.format(BASIC_URL_FORMAT, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        ClusterManagementEndpoint endpoint = target.proxy(ClusterManagementEndpoint.class);
        endpoint.unregister(host);
    }
}
