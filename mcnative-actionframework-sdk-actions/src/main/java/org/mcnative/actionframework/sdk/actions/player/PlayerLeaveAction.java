package org.mcnative.actionframework.sdk.actions.player;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.util.UUID;

public class PlayerLeaveAction implements MAFAction {

    public static final String NAMESPACE = "ply";
    public static final String NAME = "join";

    private UUID uniqueId;

    public PlayerLeaveAction(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
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
    public void read(ByteBuf buffer) {
        try {
            uniqueId = BufferUtil.readUniqueId(buffer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf buffer) {
        BufferUtil.writeUniqueId(buffer,this.uniqueId);
    }
}
