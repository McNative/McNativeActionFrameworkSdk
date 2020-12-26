package org.mcnative.actionframework.common.protocol.packet.handshake;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.common.ClientType;
import org.mcnative.actionframework.common.protocol.codec.BufferUtil;
import org.mcnative.actionframework.common.protocol.packet.Packet;
import org.mcnative.actionframework.common.protocol.packet.handshake.authentication.Authentication;
import org.mcnative.actionframework.common.protocol.packet.handshake.authentication.AuthenticationMethod;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

import java.util.UUID;

public class HandshakePacket implements Packet {

    public static byte IDENTIFIER = 0;

    private UUID uniqueId;
    private String name;
    public ClientType type;
    private Authentication authentication;

    public HandshakePacket(UUID uniqueId, String name, ClientType type, Authentication authentication) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.type = type;
        this.authentication = authentication;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public ClientType getType() {
        return type;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public byte getId() {
        return IDENTIFIER;
    }

    @Override
    public void writeBody(SerializerRegistry registry, ByteBuf buffer) {
        BufferUtil.writeUniqueId(buffer,uniqueId);
        BufferUtil.writeString(buffer,name);
        buffer.writeByte(type.getId());
        buffer.writeByte(this.authentication.getMethod().getId());
        this.authentication.write(buffer);
    }

    @Override
    public void readBody(SerializerRegistry registry, ByteBuf buffer) {
        this.uniqueId = BufferUtil.readUniqueId(buffer);
        this.name = BufferUtil.readString(buffer);
        this.type = ClientType.of(buffer.readByte());
        this.authentication = AuthenticationMethod.create(buffer.readByte());
        this.authentication.read(buffer);
    }
}
