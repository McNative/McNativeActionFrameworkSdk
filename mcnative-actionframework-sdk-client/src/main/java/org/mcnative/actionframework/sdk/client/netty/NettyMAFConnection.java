package org.mcnative.actionframework.sdk.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import net.pretronic.libraries.logging.PretronicLogger;
import net.pretronic.libraries.utility.NettyUtil;
import net.pretronic.libraries.utility.Validate;
import org.mcnative.actionframework.sdk.client.MAFClient;
import org.mcnative.actionframework.sdk.client.MAFClientConfiguration;
import org.mcnative.actionframework.sdk.client.MAFConnection;
import org.mcnative.actionframework.sdk.common.protocol.MAFProtocol;
import org.mcnative.actionframework.sdk.common.protocol.packet.Packet;
import org.mcnative.actionframework.sdk.common.protocol.packet.PacketTransport;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class NettyMAFConnection implements MAFConnection {

    private final MAFClient client;
    private final EventLoopGroup worker;
    private final Bootstrap clientBootstrap;
    private final MAFConnectionHandler handler;
    private Channel channel;

    public NettyMAFConnection(MAFClient client, MAFClientConfiguration config){
        Validate.notNull(client,config);
        this.client = client;
        this.worker = NettyUtil.newEventLoopGroup();
        int reconnectCount = config.isAutoReconnect() ? config.getMaxReconnectCount() : 0;
        this.handler = new MAFConnectionHandler(this,client.getStatusListener(),reconnectCount);
        this.clientBootstrap = new Bootstrap()
                .group(worker)
                .option(ChannelOption.AUTO_READ, true)
                .handler(new ClientChannelInitializer())
                .channel(NettyUtil.getSocketChannelClass());
    }

    @Override
    public MAFClient getClient() {
        return client;
    }

    @Override
    public PretronicLogger getLogger() {
        return client.getLogger();
    }

    @Override
    public boolean isConnected() {
        return channel != null;
    }

    @Override
    public void establish(InetSocketAddress address){
        this.channel = clientBootstrap.connect(address).syncUninterruptibly().channel();
        if(this.client.getStatusListener() != null) this.client.getStatusListener().onConnect();
    }

    @Override
    public void close() {
        this.channel.close();
        this.worker.shutdownGracefully();
        this.channel = null;
    }

    @Override
    public void sendPacket(UUID uniqueId, Packet packet) {
        Validate.notNull(uniqueId,packet);
        this.channel.writeAndFlush(new PacketTransport(uniqueId,packet));
    }

    @Override
    public <T extends Packet> CompletableFuture<T> sendPacketQuery(Class<T> result, Packet packet) {
        Validate.notNull(result,packet);
        UUID uniqueId = UUID.randomUUID();
        sendPacket(uniqueId,packet);
        CompletableFuture<T> future = new CompletableFuture<>();
        this.handler.registerFuture(uniqueId,future);
        return future;
    }

    private class ClientChannelInitializer extends ChannelInitializer<Channel> {

        protected void initChannel(Channel channel){
            MAFProtocol.initializeChannel(channel,null,null);
            channel.pipeline().addLast(NettyMAFConnection.this.handler);
        }
    }

}
