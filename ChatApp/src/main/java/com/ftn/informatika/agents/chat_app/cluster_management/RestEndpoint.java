package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
public class RestEndpoint implements RestEndpointInterface {

    @EJB
    private ClusterManagementLocal clusterManagementBean;

    @POST
    public List<Host> register(String address, String alias)
            throws AliasExistsException {
        return null;
    }


}
