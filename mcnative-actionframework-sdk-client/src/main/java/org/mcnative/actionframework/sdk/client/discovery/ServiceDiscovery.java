package org.mcnative.actionframework.sdk.client.discovery;

import java.net.InetSocketAddress;
import java.util.List;

public interface ServiceDiscovery {

    List<InetSocketAddress> getAddresses();

}
