package org.mcnative.actionframework.client.discovery;

import java.net.InetSocketAddress;
import java.util.List;

public interface ServiceDiscovery {

    List<InetSocketAddress> getAddresses();

}
