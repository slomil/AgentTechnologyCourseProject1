package com.ftn.informatika.agents.chat_app.cluster_management;

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
//@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class HostsDbBean implements HostsDbLocal {
    private Map<String, Host> hosts = new HashMap<>();

    //@Lock(LockType.WRITE)
    @Override
    public void addHost(Host host) throws AliasExistsException {
        if (hosts.containsKey(host.getAddress())) {
            throw new AliasExistsException();
        }

        hosts.put(host.getAddress(), host);
    }

    //@Lock(LockType.WRITE)
    @Override
    public void removeHost(Host host) throws HostNotExistsException {
        if (hosts.remove(host.getAddress()) == null) {
            throw new HostNotExistsException();
        }
    }

    //@Lock(LockType.READ)
    @Override
    public boolean containsHost(Host host) {
        return hosts.containsKey(host.getAddress());
    }

    //@Lock(LockType.READ)
    @Override
    public List<Host> getHosts() {
        return new ArrayList<>(hosts.values());
    }
}
