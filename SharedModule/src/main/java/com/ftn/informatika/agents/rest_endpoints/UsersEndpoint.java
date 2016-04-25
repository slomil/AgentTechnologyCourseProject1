package com.ftn.informatika.agents.rest_endpoints;

import com.ftn.informatika.agents.exception.*;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;



/**
 * @author - Srđan Milaković
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UsersEndpoint {

    @POST
    @Path("/register")
    User register(@QueryParam("username") String username, @QueryParam("password") String password)
            throws UsernameExistsException, InsufficientDataException;

    @POST
    @Path("/login")
    User login(@QueryParam("username") String username, @QueryParam("password") String password,
                      @QueryParam("hostAddress") String hostAddress, @QueryParam("hostAlias") String hostAlias)
            throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException;
    @POST
    @Path("/logout")
     Boolean logout(User user);

    @POST
    @Path("/users")
    List<User> getAllUsers();
}

