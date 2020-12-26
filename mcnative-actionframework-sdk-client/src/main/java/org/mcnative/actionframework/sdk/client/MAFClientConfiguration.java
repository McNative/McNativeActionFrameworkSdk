package org.mcnative.actionframework.sdk.client;

import org.mcnative.actionframework.sdk.common.ClientType;
import org.mcnative.actionframework.sdk.common.protocol.packet.handshake.authentication.Authentication;

import java.util.UUID;

public class MAFClientConfiguration {

    private final UUID uniqueId;
    private final String name;
    private final ClientType type;
    private final Authentication authentication;

    private final boolean autoReconnect;
    private final int maxReconnectCount;

    public MAFClientConfiguration(UUID uniqueId, String name, ClientType type, Authentication authentication, boolean autoReconnect, int maxReconnectCount) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.type = type;
        this.authentication = authentication;
        this.autoReconnect = autoReconnect;
        this.maxReconnectCount = maxReconnectCount;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public ClientType getType() {
        return type;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public int getMaxReconnectCount() {
        return maxReconnectCount;
    }
}
