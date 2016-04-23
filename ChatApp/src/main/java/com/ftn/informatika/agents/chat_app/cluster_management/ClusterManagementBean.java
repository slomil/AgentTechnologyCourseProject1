package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class ClusterManagementBean implements ClusterManagementLocal {

    private Map<String, Host> hosts = new HashMap<>();

    @EJB
    private ServerManagementLocal serverManagementBean;


    @Lock(LockType.WRITE)
    @Override
    public List<Host> register(String address, String alias) throws AliasExistsException {
        if (hosts.containsKey(address)) {
            throw new AliasExistsException();
        }

        if (serverManagementBean.isMaster()) {

        }
        return null;
    }

    @Lock(LockType.WRITE)
    @Override
    public void unregister(Host host) throws HostNotExistsException {

    }

    @Lock(LockType.READ)
    @Override
    public List<Host> getHosts() {
        return new ArrayList<>(hosts.values());
    }

    private void notifySlaveNodes() {
        hosts.values().forEach(h -> {

        });
    }

}
