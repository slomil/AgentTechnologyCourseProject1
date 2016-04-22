package com.ftn.informatika.agents.chat_app.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author - Srđan Milaković
 */
@Startup
@Singleton
public class ServerManagement {

    @PostConstruct
    public void postConstruct() {
        String val = System.getProperty("master");
        System.out.println(val);
    }

    @PreDestroy
    public void preDestroy() {

    }
}
