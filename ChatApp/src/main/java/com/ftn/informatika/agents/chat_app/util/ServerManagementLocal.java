package com.ftn.informatika.agents.chat_app.util;

import javax.ejb.Local;

/**
 * @author - Srđan Milaković
 */
@Local
public interface ServerManagementLocal {
    String getMasterAddress();
    String getLocalAddress();
    String getLocalHostName();
    boolean isMaster();
}
