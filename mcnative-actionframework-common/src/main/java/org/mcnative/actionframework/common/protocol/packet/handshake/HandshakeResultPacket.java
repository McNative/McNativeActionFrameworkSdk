package org.mcnative.actionframework.common.protocol.packet.handshake;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.common.ClientType;
import org.mcnative.actionframework.common.protocol.codec.BufferUtil;
import org.mcnative.actionframework.common.protocol.packet.Packet;
import org.mcnative.actionframework.common.protocol.packet.handshake.authentication.Authentication;
import org.mcnative.actionframework.common.protocol.packet.handshake.authentication.AuthenticationMethod;
import org.mcnative.actionframework.common.protocol.packet.handshake.authentication.AuthenticationResult;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

import java.util.UUID;

public class HandshakeResultPacket implements Packet {

    public static byte IDENTIFIER = 1;

    private AuthenticationResult result;

    public HandshakeResultPacket(AuthenticationResult result) {
        this.result = result;
    }

    public AuthenticationResult getResult() {
        return result;
    }

    @Override
    public byte getId() {
        return IDENTIFIER;
    }

    @Override
    public void writeBody(SerializerRegistry registry, ByteBuf buffer) {
        this.result.write(buffer);
    }

    @Override
    public void readBody(SerializerRegistry registry, ByteBuf buffer) {
        this.result = new AuthenticationResult();
        this.result.read(buffer);
    }
}
