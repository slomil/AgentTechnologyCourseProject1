package com.ftn.informatika.agents.chat_app.util;

import com.ftn.informatika.agents.chat_app.cluster_management.ClusterManagementRequester;
import com.ftn.informatika.agents.chat_app.cluster_management.HostsDbLocal;
import com.ftn.informatika.agents.chat_app.users.UserAppJmsLocal;
import com.ftn.informatika.agents.chat_app.users.UserAppRequester;
import com.ftn.informatika.agents.chat_app.users.UsersDbLocal;
import com.ftn.informatika.agents.exception.AliasExistsException;
import com.ftn.informatika.agents.exception.HostNotExistsException;
import com.ftn.informatika.agents.model.Host;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import javax.jms.JMSException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Startup
@Singleton
public class ServerManagementBean implements ServerManagementLocal {
    private static final int DEFAULT_PORT = 8080;
    private static final String MASTER_ADDRESS_KEY = "master";
    private static final String LOCAL_ADDRESS_KEY = "local";
    private static final String ALIAS_KEY = "alias";
    private static final String PORT_OFFSET_KEY = "jboss.socket.binding.port-offset";

    @EJB
    private HostsDbLocal hostsDbBean;
    @EJB
    private UsersDbLocal usersDbBean;
    @EJB
    private UserAppJmsLocal userAppJmsBean;

    private String masterAddress;
    private Host localHost;

    @Lock(LockType.WRITE)
    @PostConstruct
    public void postConstruct() {
        String localAddress;
        String hostName;
        if ((masterAddress = System.getProperty(MASTER_ADDRESS_KEY)) == null) {
            System.out.println("This is master node!");
        } else {
            System.out.println("This is slave node! Master node is at: " + masterAddress);
        }

        // Get local IP address
        if ((localAddress = System.getProperty(LOCAL_ADDRESS_KEY)) == null) {
            try {
                String portOffset = System.getProperty(PORT_OFFSET_KEY);
                int port = DEFAULT_PORT + (portOffset != null ? Integer.parseInt(portOffset) : 0);
                InetAddress local = InetAddress.getLocalHost();
                localAddress = local.getHostAddress() + ":" + port;

            } catch (UnknownHostException e) {
                System.err.println("Can't read Local IPv4 Address.");
            } catch (NumberFormatException e) {
                System.err.println("Cannot parse port offset.");
            }
        }
        System.out.println("Local IPv4 Address: " + localAddress);

        // Get alias
        if ((hostName = System.getProperty(ALIAS_KEY)) == null) {
            try {
                InetAddress local = InetAddress.getLocalHost();
                hostName = local.getHostName();
            } catch (UnknownHostException e) {
                System.err.println("Can't read Host Name.");
            }
        }
        System.out.println("Host Name: " + hostName);

        localHost = new Host(localAddress, hostName);

        // Register to master node

        try {
            if (!isMaster()) {
                ClusterManagementRequester.register(masterAddress, localAddress, hostName).forEach(h -> {
                    try {
                        hostsDbBean.addHost(h);
                    } catch (AliasExistsException e) {
                        System.err.println("Alias \"" + h.getAlias() + "\" already exists.");
                    }
                });
            } else {
                hostsDbBean.addHost(localHost);
            }
        } catch (AliasExistsException e) {
            System.err.println("Can not register node. Alias \"" + localHost.getAlias() + "\" already exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (isMaster()) {
            try {
                userAppJmsBean.getAllUsers();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            usersDbBean.setUsers(UserAppRequester.getAllUsers(masterAddress));
        }

    }

    @PreDestroy
    public void preDestroy() {
        // Unregister
        if (!isMaster()) {
            try {
                ClusterManagementRequester.unregister(masterAddress, localHost);
            } catch (HostNotExistsException e) {
                System.err.println("Host \"" + localHost + "\" does not exist.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Lock(LockType.READ)
    @Override
    public String getMasterAddress() {
        return masterAddress;
    }

    @Lock(LockType.READ)
    @Override
    public String getLocalAddress() {
        return localHost.getAddress();
    }

    @Lock(LockType.READ)
    public String getAlias() {
        return localHost.getAlias();
    }

    @Lock(LockType.READ)
    @Override
    public Host getHost() {
        return localHost;
    }

    @Lock(LockType.READ)
    @Override
    public boolean isMaster() {
        return masterAddress == null;
    }
}
