package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/cluster_management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestEndpointInterface {
    @POST
    @Path("/register")
    List<Host> register(@QueryParam("address") String address, @QueryParam("alias") String alias)
            throws AliasExistsException;
}
