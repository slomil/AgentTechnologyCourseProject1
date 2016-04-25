package com.ftn.informatika.agents.chat_app.util;

import com.ftn.informatika.agents.model.Host;

import javax.ejb.Local;

/**
 * @author - Srđan Milaković
 */
@Local
public interface ServerManagementLocal {
    String getMasterAddress();
    Host getHost();
    String getLocalAddress();
    String getAlias();
    boolean isMaster();
}
