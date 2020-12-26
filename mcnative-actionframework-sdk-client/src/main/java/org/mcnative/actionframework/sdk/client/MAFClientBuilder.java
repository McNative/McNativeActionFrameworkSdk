package org.mcnative.actionframework.sdk.client;

import net.pretronic.libraries.logging.PretronicLogger;
import net.pretronic.libraries.logging.PretronicLoggerFactory;
import org.mcnative.actionframework.sdk.client.discovery.ServiceDiscovery;
import org.mcnative.actionframework.sdk.common.ClientType;
import org.mcnative.actionframework.sdk.common.protocol.packet.handshake.authentication.Authentication;

import java.util.UUID;

public class MAFClientBuilder {

    private UUID uniqueId;
    private String name;
    private ClientType type;
    private Authentication authentication;
    private boolean autoReconnect = false;
    private int maxReconnectCount = 3;

    private ServiceDiscovery serviceDiscovery;
    private StatusListener statusListener;
    private PretronicLogger logger;

    protected MAFClientBuilder(){}

    public MAFClientBuilder uniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public MAFClientBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MAFClientBuilder type(ClientType type) {
        this.type = type;
        return this;
    }

    public MAFClientBuilder authentication(Authentication authentication) {
        this.authentication = authentication;
        return this;
    }

    public MAFClientBuilder autoReconnect() {
        this.autoReconnect = true;
        return this;
    }

    public MAFClientBuilder autoReconnect(int maxCount) {
        this.autoReconnect = true;
        this.maxReconnectCount = maxCount;
        return this;
    }

    public MAFClientBuilder serviceDiscovery(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
        return this;
    }

    public MAFClientBuilder statusListener(StatusListener statusListener) {
        this.statusListener = statusListener;
        return this;
    }

    public MAFClientBuilder logger(PretronicLogger logger) {
        this.logger = logger;
        return this;
    }

    public MAFClient create() {
        if(logger == null) logger = PretronicLoggerFactory.getLogger(MAFClient.class);
        return new DefaultMAFClient(logger,statusListener,serviceDiscovery
                ,new MAFClientConfiguration(uniqueId,name,type,authentication,autoReconnect,maxReconnectCount));
    }
}
