package com.ftn.informatika.agents.rest_endpoints;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Path("/cluster_management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ClusterManagementEndpoint {
    @POST
    @Path("/register")
    List<Host> register(@QueryParam("address") String address, @QueryParam("alias") String alias)
            throws AliasExistsException;

    @POST
    @Path("/unregister")
    void unregister(Host host) throws HostNotExistsException;

    @GET
    @Path("/hosts")
    List<Host> getHosts();
}
