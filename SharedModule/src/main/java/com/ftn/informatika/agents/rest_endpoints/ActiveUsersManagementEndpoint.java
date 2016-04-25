package com.ftn.informatika.agents.rest_endpoints;

import com.ftn.informatika.agents.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author - Srđan Milaković
 */
@Path("/users_management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ActiveUsersManagementEndpoint {
    @POST
    void addUser(User user);

    @DELETE
    void removeUser(User user);
}
