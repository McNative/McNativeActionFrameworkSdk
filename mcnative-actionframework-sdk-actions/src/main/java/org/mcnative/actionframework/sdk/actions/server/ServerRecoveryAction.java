package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerRecoveryAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "recovery";

    private Map<UUID, Integer> onlinePlayers;

    public ServerRecoveryAction(Map<UUID, Integer> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public Map<UUID, Integer> getOnlinePlayers() {
        return onlinePlayers;
    }

    @Override
    public void read(int version, ByteBuf buffer) {
        int size = buffer.readInt();
        this.onlinePlayers = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            this.onlinePlayers.put(BufferUtil.readUniqueId(buffer), buffer.readInt());
        }
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(this.onlinePlayers.size());
        for (Map.Entry<UUID, Integer> entry : onlinePlayers.entrySet()) {
            BufferUtil.writeUniqueId(buffer, entry.getKey());
            buffer.writeInt(entry.getValue());
        }
    }
}
