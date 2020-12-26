package org.mcnative.actionframework.sdk.common.protocol.packet.handshake.authentication;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.util.UUID;

public class KeyAuthentication implements Authentication {

    private UUID networkId;
    private String secret;

    public KeyAuthentication(UUID networkId, String secret) {
        this.networkId = networkId;
        this.secret = secret;
    }

    public UUID getNetworkId() {
        return networkId;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public void write(ByteBuf buffer) {
        BufferUtil.writeUniqueId(buffer,this.networkId);
        BufferUtil.writeString(buffer,secret);
    }

    @Override
    public void read(ByteBuf buffer) {
        this.networkId =  BufferUtil.readUniqueId(buffer);
        this.secret =  BufferUtil.readString(buffer);
    }

    @Override
    public AuthenticationMethod getMethod() {
        return AuthenticationMethod.NETWORK_KEY;
    }
}
