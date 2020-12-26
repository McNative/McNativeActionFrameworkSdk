package org.mcnative.actionframework.sdk.common.action;

import java.util.UUID;

public interface MAFActionExecutor {

    UUID getNetworkId();

    UUID getClientId();

    default String getNetworkIdShort(){
        return getNetworkId().getMostSignificantBits()+"/"+getNetworkId().getLeastSignificantBits();
    }

    default String getClientIdShort(){
        return getClientId().getMostSignificantBits()+"/"+getClientId().getLeastSignificantBits();
    }

}
