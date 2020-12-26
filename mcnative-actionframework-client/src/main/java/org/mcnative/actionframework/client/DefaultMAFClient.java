package org.mcnative.actionframework.client;

import net.pretronic.libraries.logging.PretronicLogger;
import net.pretronic.libraries.utility.Validate;
import net.pretronic.libraries.utility.exception.OperationFailedException;
import net.pretronic.libraries.utility.reflect.UnsafeInstanceCreator;
import org.mcnative.actionframework.client.discovery.ServiceDiscovery;
import org.mcnative.actionframework.client.netty.NettyMAFConnection;
import org.mcnative.actionframework.common.action.MAFAction;
import org.mcnative.actionframework.common.action.MAFActionListener;
import org.mcnative.actionframework.common.action.MAFActionSubscription;
import org.mcnative.actionframework.common.protocol.packet.ActionPacket;
import org.mcnative.actionframework.common.protocol.packet.handshake.HandshakePacket;
import org.mcnative.actionframework.common.protocol.packet.handshake.HandshakeResultPacket;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultMAFClient implements MAFClient {

    private final PretronicLogger logger;
    private final MAFClientConfiguration configuration;
    private final StatusListener statusListener;
    private final ServiceDiscovery discovery;
    private final MAFConnection connection;

    private final Collection<MAFActionSubscription> subscriptions;

    protected DefaultMAFClient(PretronicLogger logger,StatusListener statusListener,ServiceDiscovery discovery,MAFClientConfiguration configuration) {
        this.logger = logger;
        this.configuration = configuration;
        this.discovery = discovery;
        this.connection = new NettyMAFConnection(this,configuration);
        this.statusListener = statusListener;

        this.subscriptions = new ArrayList<>();
    }

    @Override
    public PretronicLogger getLogger() {
        return logger;
    }

    @Override
    public ServiceDiscovery getServiceDiscovery() {
        return discovery;
    }

    @Override
    public MAFConnection getConnection() {
        return connection;
    }

    @Override
    public StatusListener getStatusListener() {
        return statusListener;
    }

    @Override
    public void connect() {
        Exception last = null;
        for (InetSocketAddress address : discovery.getAddresses()) {
            try {
                this.connection.establish(address);
            }catch (Exception e){
                last = e;
                continue;
            }
            HandshakeResultPacket result = this.connection.sendPacketQuerySync(HandshakeResultPacket.class
                    ,new HandshakePacket(configuration.getUniqueId(),configuration.getName()
                    ,configuration.getType(),configuration.getAuthentication()));
            if(!result.getResult().isSuccessful()){
                throw new MAFConnectionFailedException(new OperationFailedException("Authentication failed ("+result.getResult().getMessage()+")"));
            }
            return;
        }
        if(last == null){
            throw new MAFConnectionFailedException(new OperationFailedException("No host available"));
        }else{
            throw new MAFConnectionFailedException(last);
        }
    }

    @Override
    public void disconnect() {
        if(this.connection.isConnected()){
            this.connection.close();
        }
    }

    @Override
    public Collection<MAFActionSubscription> getSubscribedActions() {
        return this.subscriptions;
    }

    @Override
    public void sendAction(MAFAction action) {
        Validate.notNull(action);
        action.validate();
        this.connection.sendPacket(new ActionPacket(action));
    }

    @Override
    public <T extends MAFAction> void registerAction(Class<T> actionClass, MAFActionListener<T> listener) {
        Validate.notNull(actionClass,listener);
        MAFAction action = UnsafeInstanceCreator.newInstance(actionClass);
        this.subscriptions.add(new MAFActionSubscription(action.getNamespace(),action.getName(),actionClass,listener));
    }
}
