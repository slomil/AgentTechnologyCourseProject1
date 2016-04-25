package com.ftn.informatika.agents.user_app.endpoint;

import com.ftn.informatika.agents.exception.*;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.UsersEndpoint;
import com.ftn.informatika.agents.user_app.bean.UserDbLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class UsersREST implements UsersEndpoint {

    @EJB
    private UserDbLocal userDbBean;

    @Override
    public User register(String username, String password) throws UsernameExistsException, InsufficientDataException {
        return userDbBean.register(username, password);
    }

    @Override
    public User login(String username, String password, String hostAddress, String hostAlias)
            throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException {
        if (hostAddress == null || hostAlias == null) {
            throw new InsufficientDataException();
        }

        return userDbBean.login(username, password, new Host(hostAddress, hostAlias));
    }

    @Override
    public Boolean logout(User user) {
        try {
            userDbBean.logout(user);
            return true;
        } catch (UserInactiveException e) {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDbBean.getAllUsers();
    }
}
