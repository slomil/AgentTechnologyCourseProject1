package com.ftn.informatika.agents.chat_app.cluster_management;

import com.ftn.informatika.agents.model.Host;

import java.util.List;

/**
 * @author - Srđan Milaković
 */
public interface RestRequesterLocal {
    List<Host> register(String masterAddress, String address, String alias);
    void unregister(Host host);
}
