package org.mcnative.actionframework.sdk.client.discovery;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

public class StaticServiceDiscovery implements ServiceDiscovery{

    private final InetSocketAddress address;

    public StaticServiceDiscovery(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public List<InetSocketAddress> getAddresses() {
        return Collections.singletonList(this.address);
    }
}
