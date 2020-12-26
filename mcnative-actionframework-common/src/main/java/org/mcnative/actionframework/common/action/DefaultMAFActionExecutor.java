package org.mcnative.actionframework.common.action;

import java.util.UUID;

public class DefaultMAFActionExecutor implements MAFActionExecutor {

    private final UUID networkId;
    private final UUID uniqueId;

    public DefaultMAFActionExecutor(UUID networkId, UUID uniqueId) {
        this.networkId = networkId;
        this.uniqueId = uniqueId;
    }

    @Override
    public UUID getNetworkId() {
        return networkId;
    }

    @Override
    public UUID getClientId() {
        return uniqueId;
    }
}
