package org.mcnative.actionframework.sdk.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.pretronic.libraries.utility.Iterators;
import org.mcnative.actionframework.sdk.client.MAFConnection;
import org.mcnative.actionframework.sdk.client.StatusListener;
import org.mcnative.actionframework.sdk.common.action.MAFActionSubscription;
import org.mcnative.actionframework.sdk.common.protocol.packet.ActionPacket;
import org.mcnative.actionframework.sdk.common.protocol.packet.Packet;
import org.mcnative.actionframework.sdk.common.protocol.packet.PacketTransport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MAFConnectionHandler extends SimpleChannelInboundHandler<PacketTransport> {

    private final MAFConnection connection;
    public final StatusListener statusListener;
    public final int reconnectCount;
    public final Map<UUID, CompletableFuture> futures;

    protected MAFConnectionHandler(MAFConnection connection,StatusListener statusListener, int reconnectCount) {
        this.futures = new HashMap<>();
        this.connection = connection;
        this.statusListener = statusListener;
        this.reconnectCount = reconnectCount;
    }

    public void registerFuture(UUID uniqueId,CompletableFuture future){
        this.futures.put(uniqueId,future);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)  {
        ctx.channel().close();
        ctx.close();
        if(statusListener != null) statusListener.onDisconnect();
        if(reconnectCount > 0){
            for (int i = 0; i < reconnectCount; i++) {
                try{
                    connection.getClient().connect();
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        connection.getLogger().error(cause);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketTransport transport) {
        CompletableFuture future = futures.get(transport.getTransactionId());
        if(future != null) future.complete(transport.getPacket());
        else{
            Packet packet = transport.getPacket();
            if(packet instanceof ActionPacket){
                ActionPacket aPacket = (ActionPacket) packet;
                MAFActionSubscription subscription = Iterators.findOne(connection.getClient().getSubscribedActions(), sub
                        -> sub.getNamespace().equalsIgnoreCase(aPacket.getNamespace()) && sub.getName().equalsIgnoreCase(aPacket.getName()));
                if(subscription != null){
                    subscription.getListener().callListener(null,aPacket.getAction());
                }
            }
        }
    }
}
