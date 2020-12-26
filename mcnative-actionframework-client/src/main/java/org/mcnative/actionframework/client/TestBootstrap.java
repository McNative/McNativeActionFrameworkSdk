package org.mcnative.actionframework.client;

import org.mcnative.actionframework.client.discovery.StaticServiceDiscovery;
import org.mcnative.actionframework.common.ClientType;
import org.mcnative.actionframework.common.action.player.PlayerJoinAction;
import org.mcnative.actionframework.common.protocol.packet.handshake.authentication.KeyAuthentication;

import java.net.InetSocketAddress;
import java.util.UUID;

public class TestBootstrap {

    public static void main(String[] args)throws Exception {
        //new DnsServiceDiscovery("_maf._tcp.mcnative.org")

        MAFClient client = MAFClient.build()
                .serviceDiscovery(new StaticServiceDiscovery(new InetSocketAddress("localhost",9730)))
                .uniqueId(UUID.randomUUID())
                .name("Test")
                .type(ClientType.GENERIC)
                .authentication(new KeyAuthentication(UUID.fromString("bc03985a-c657-4af7-b6d9-a3dabf06a57b"), "htsoC8mEmCA*,Pvu,PvRyeONZTJC2Su&xa#*L.UW"))
                .create();

        client.connect();

        for (int i = 0; i < 5 ; i++) {
            client.sendAction(new PlayerJoinAction(UUID.randomUUID()));
        }
    }
}
