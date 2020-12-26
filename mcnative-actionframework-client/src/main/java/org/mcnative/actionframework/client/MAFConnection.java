package org.mcnative.actionframework.client;

import net.pretronic.libraries.logging.PretronicLogger;
import net.pretronic.libraries.utility.exception.OperationFailedException;
import org.mcnative.actionframework.common.protocol.packet.Packet;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface MAFConnection {

    MAFClient getClient();

    PretronicLogger getLogger();

    boolean isConnected();

    void establish(InetSocketAddress address);

    void close();

    default void sendPacket(Packet packet){
        sendPacket(UUID.randomUUID(),packet);
    }

    void sendPacket(UUID uniqueId, Packet packet);

    <T extends Packet> CompletableFuture<T> sendPacketQuery(Class<T> result,Packet packet);

    default <T extends Packet> T sendPacketQuerySync(Class<T> result,Packet packet){
        try {
            return sendPacketQuery(result,packet).get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new OperationFailedException(e);
        }
    }

}
