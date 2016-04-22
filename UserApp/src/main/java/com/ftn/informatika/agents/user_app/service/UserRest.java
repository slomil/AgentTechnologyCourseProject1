package com.ftn.informatika.agents.user_app.service;

import exception.AlreadyRegisteredException;
import exception.InsufficientDataException;
import exception.InvalidCredentialsException;
import exception.UsernameExistsException;
import model.Host;
import model.User;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/api")
public class UserRest {

    @Path("/register")
    User register(@QueryParam("username") String username, @QueryParam("password") String password)
            throws UsernameExistsException, InsufficientDataException {

    }

    @Path("/login")
    Boolean login(@QueryParam("username") String username, String password, Host host)
            throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException {

    }

    @Path("/logout")
    Boolean logout(User logout) {

    }

    @Path("/users")
    List<User> getAllUsers() {

    }
}
