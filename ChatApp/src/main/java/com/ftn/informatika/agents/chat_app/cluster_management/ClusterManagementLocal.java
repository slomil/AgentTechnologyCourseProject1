package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface ClusterManagementLocal {
    List<Host> register(String address, String alias) throws AliasExistsException;
    void unregister(Host host) throws HostNotExistsException;
    List<Host> getHosts();
}
