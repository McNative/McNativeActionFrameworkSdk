package org.mcnative.actionframework.sdk.actions.player;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.util.UUID;

public class PlayerJoinAction implements MAFAction {

    public static final String NAMESPACE = "ply";
    public static final String NAME = "join";

    private UUID uniqueId;
    private int protocolVersion;

    public PlayerJoinAction(UUID uniqueId,int protocolVersion) {
        this.uniqueId = uniqueId;
        this.protocolVersion = protocolVersion;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void read(int version,ByteBuf buffer) {
        try {
            uniqueId = BufferUtil.readUniqueId(buffer);
            protocolVersion = buffer.readInt();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf buffer) {
        BufferUtil.writeUniqueId(buffer,this.uniqueId);
        buffer.writeInt(this.protocolVersion);
    }
}
