package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.chat_app.db_beans.HostsDbLocal;
import com.ftn.informatika.agents.chat_app.requestors.ClusterManagementRequester;
import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ejb.*;
import java.util.List;

/**
 * @author - Srđan Milaković
 */

@Stateless
public class ClusterManagementBean implements ClusterManagementLocal {

    @EJB
    private ServerManagementLocal serverManagementBean;
    @EJB
    private HostsDbLocal hostsDbBean;

    @Override
    public List<Host> register(String address, String alias) throws AliasExistsException {
        Host host = new Host(address, alias);
        if (hostsDbBean.containsHost(host)) {
            throw new AliasExistsException();
        }

        // Notify other slave nodes
        if (serverManagementBean.isMaster()) {
            hostsDbBean.getHosts().forEach(h -> {
                if (!serverManagementBean.getLocalAddress().equals(h.getAddress())) {
                    try {
                        ClusterManagementRequester.register(h.getAddress(), address, alias);
                    } catch (AliasExistsException e) {
                        System.err.println("Alias \"" + alias + "\" already exists.");
                    }
                }
            });
        }

        hostsDbBean.addHost(host);
        System.out.println("Host \"" + host + "\" is registered to node \"" + serverManagementBean.getAlias() + "\".");

        return hostsDbBean.getHosts();
    }

    @Override
    public void unregister(Host host) throws HostNotExistsException {
        hostsDbBean.removeHost(host);

        // Unregister removed host from other slave hosts
        if (serverManagementBean.isMaster()) {
            hostsDbBean.getHosts().forEach(h -> {
                if (!serverManagementBean.getLocalAddress().equals(h.getAddress())) {
                    try {
                        ClusterManagementRequester.unregister(h.getAddress(), host);
                    } catch (HostNotExistsException e) {
                        System.err.println("Host " + host + " does not exist.");
                    }
                }
            });
        }

        System.out.println("Host \"" + host + "\" is unregistered from node \"" + serverManagementBean.getAlias() + "\".");
    }

    @Override
    public List<Host> getHosts() {
        return hostsDbBean.getHosts();
    }
}
