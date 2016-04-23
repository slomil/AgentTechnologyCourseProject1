package com.ftn.informatika.agents.chat_app.util;

import com.ftn.informatika.agents.chat_app.cluster_management.RestRequesterLocal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.net.*;
import java.util.Collections;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Startup
@Singleton
public class ServerManagementBean implements ServerManagementLocal {
    private static final String MASTER_ADDRESS_KEY = "master";

    @EJB
    private RestRequesterLocal restRequesterBean;

    private String masterAddress;
    private String localAddress;
    private String localHostName;

    @Lock(LockType.WRITE)
    @PostConstruct
    public void postConstruct() {
        if ((masterAddress = System.getProperty(MASTER_ADDRESS_KEY)) == null) {
            System.out.println("This is master node!");
        } else {
            System.out.println("This is slave node! Master node is at: " + masterAddress);
        }

        try {
            InetAddress local = InetAddress.getLocalHost();
            System.out.println("Local IPv4 address: " + (localAddress = local.getHostAddress()));
            System.out.println("Local host name: " + (localHostName = local.getHostName()));
        } catch (UnknownHostException e) {
            System.out.println("Can't read local IPv4 address.");
        }

        // Register to master node
        if (masterAddress != null) {
            restRequesterBean.register(masterAddress, localAddress, localHostName);
        }
    }

    @PreDestroy
    public void preDestroy() {

    }

    @Lock(LockType.READ)
    @Override
    public String getMasterAddress() {
        return masterAddress;
    }

    @Lock(LockType.READ)
    @Override
    public String getLocalAddress() {
        return localAddress;
    }

    @Lock(LockType.READ)
    @Override
    public String getLocalHostName() {
        return localHostName;
    }

    @Override
    public boolean isMaster() {
        return masterAddress == null;
    }
}
