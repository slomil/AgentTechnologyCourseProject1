package com.ftn.informatika.agents.chat_app.db_beans;

import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface HostsDbLocal {
    void addHost(Host host) throws AliasExistsException;
    void removeHost(Host host) throws HostNotExistsException;
    boolean containsHost(Host host);
    List<Host> getHosts();
}
