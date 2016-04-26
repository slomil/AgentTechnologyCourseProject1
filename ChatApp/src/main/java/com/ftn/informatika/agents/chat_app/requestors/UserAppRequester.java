package com.ftn.informatika.agents.chat_app.requestors;

import com.ftn.informatika.agents.exception.AlreadyRegisteredException;
import com.ftn.informatika.agents.exception.InsufficientDataException;
import com.ftn.informatika.agents.exception.InvalidCredentialsException;
import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.UsersEndpoint;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class UserAppRequester {
    private static final String USER_APP_URL = "http://%s/user_app/api/";

    public static User register(String destinationAddress, String username, String password)
            throws UsernameExistsException, InsufficientDataException {
        return createProxy(destinationAddress).register(username, password);
    }

    public static User login(String destinationAddress, String username, String password, Host host)
            throws InsufficientDataException, AlreadyRegisteredException, InvalidCredentialsException {
        return createProxy(destinationAddress).login(username, password, host.getAddress(), host.getAlias());
    }

    public static boolean logout(String destinationAddress, User user) {
        return createProxy(destinationAddress).logout(user);
    }

    public static List<User> getAllUsers(String destinationAddress) {
        return createProxy(destinationAddress).getAllUsers();
    }

    private static UsersEndpoint createProxy(String destinationAddress) {
        String url = String.format(USER_APP_URL, destinationAddress);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        return target.proxy(UsersEndpoint.class);
    }
}
