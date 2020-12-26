package org.mcnative.actionframework.client;

import net.pretronic.libraries.logging.PretronicLogger;
import org.mcnative.actionframework.client.discovery.ServiceDiscovery;
import org.mcnative.actionframework.common.action.MAFAction;
import org.mcnative.actionframework.common.action.MAFActionListener;
import org.mcnative.actionframework.common.action.MAFActionSubscription;

import java.util.Collection;

public interface MAFClient {

    PretronicLogger getLogger();

    ServiceDiscovery getServiceDiscovery();

    MAFConnection getConnection();

    StatusListener getStatusListener();


    void connect();

    void disconnect();


    Collection<MAFActionSubscription> getSubscribedActions();

    <T extends MAFAction> void registerAction(Class<T> action, MAFActionListener<T> listener);

    void sendAction(MAFAction action);

    public static MAFClientBuilder build(){
        return new MAFClientBuilder();
    }

}
