package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.model.Host;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class RestRequesterBean implements RestRequesterLocal {
    private static final String BASIC_URL_FORMAT = "http://%s:8080/chat_app/api/";

    @Override
    public List<Host> register(String masterAddress, String address, String alias) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String url = String.format(BASIC_URL_FORMAT, masterAddress);
                    ResteasyClient client = new ResteasyClientBuilder().build();
                    ResteasyWebTarget target = client.target(url);
                    RestEndpointInterface endpoint = target.proxy(RestEndpointInterface.class);
                    List<Host> hosts = null;
                    try {
                        hosts = endpoint.register(address, alias);
                        hosts.forEach(System.out::println);
                    } catch (AliasExistsException e) {
                        e.printStackTrace();
                    }
                    hosts.forEach(System.out::println);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return null;
    }

    @Override
    public void unregister(Host host) {

    }
}
