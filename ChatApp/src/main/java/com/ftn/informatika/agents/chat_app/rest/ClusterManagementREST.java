package com.ftn.informatika.agents.chat_app.rest;

import com.ftn.informatika.agents.chat_app.cluster_management.ClusterManagementLocal;
import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.rest_endpoints.ClusterManagementEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class ClusterManagementREST implements ClusterManagementEndpoint {

    @EJB
    private ClusterManagementLocal clusterManagementBean;

    @Override
    public List<Host> register(String address, String alias) throws AliasExistsException {
        return clusterManagementBean.register(address, alias);
    }

    @Override
    public void unregister(Host host) throws HostNotExistsException {
        clusterManagementBean.unregister(host);
    }

    @Override
    public List<Host> getHosts() {
        return clusterManagementBean.getHosts();
    }
}
