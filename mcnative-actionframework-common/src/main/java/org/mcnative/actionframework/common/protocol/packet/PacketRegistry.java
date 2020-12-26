package org.mcnative.actionframework.common.protocol.packet;

import org.mcnative.actionframework.common.protocol.packet.handshake.HandshakePacket;
import org.mcnative.actionframework.common.protocol.packet.handshake.HandshakeResultPacket;

public class PacketRegistry {

    public static Class<? extends Packet> getPacketClass(byte id){
        if(ActionPacket.IDENTIFIER == id) return ActionPacket.class;
        else if(HandshakePacket.IDENTIFIER == id) return HandshakePacket.class;
        else if(HandshakeResultPacket.IDENTIFIER == id) return HandshakeResultPacket.class;
        throw new UnsupportedOperationException("Unknown packet ("+id+")");
    }

}
