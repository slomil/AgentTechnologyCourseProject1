package com.ftn.informatika.agents.user_app.service;

import com.ftn.informatika.agents.user_app.bean.UserDbLocal;
import exception.AlreadyRegisteredException;
import exception.InsufficientDataException;
import exception.InvalidCredentialsException;
import exception.UsernameExistsException;
import model.Host;
import model.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @EJB
    private UserDbLocal userDbBean;

    @POST
    @Path("/register")
    public User register(@QueryParam("username") String username, @QueryParam("password") String password)
            throws UsernameExistsException, InsufficientDataException {
        return userDbBean.register(username, password);
    }

    @POST
    @Path("/login")
    public Boolean login(@QueryParam("username") String username, @QueryParam("password") String password,
                  @QueryParam("hostAddress") String hostAddress, @QueryParam("hostAlias") String hostAlias)
            throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException {
        if (hostAddress == null || hostAlias == null) {
            throw new InsufficientDataException();
        }

        return userDbBean.login(username, password, new Host(hostAddress, hostAlias));
    }

    @POST
    @Path("/logout")
    public Boolean logout(User user) {
        return userDbBean.logout(user);
    }

    @POST
    @Path("/users")
    public List<User> getAllUsers() {
        return userDbBean.getAllUsers();
    }
}
