package org.mcnative.actionframework.common.protocol.packet.handshake.authentication;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.common.protocol.codec.BufferUtil;
import org.mcnative.actionframework.common.serialisation.Serializable;

import java.util.UUID;

public class AuthenticationResult implements Serializable {

    private static final UUID UNKNOWN = new UUID(0,0);

    private boolean successful;
    private String message;
    private UUID networkId;

    public AuthenticationResult(){}

    public AuthenticationResult(boolean successful, String message) {
        this(successful,message,UNKNOWN);
    }

    public AuthenticationResult(boolean successful, String message,UUID networkId) {
        this.successful = successful;
        this.message = message;
        this.networkId = networkId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }

    public UUID getNetworkId() {
        return networkId;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeBoolean(successful);
        BufferUtil.writeString(buffer,message);
        BufferUtil.writeUniqueId(buffer,this.networkId);
    }

    @Override
    public void read(ByteBuf buffer) {
        this.successful = buffer.readBoolean();
        this.message = BufferUtil.readString(buffer);
        this.networkId = BufferUtil.readUniqueId(buffer);
    }
}
