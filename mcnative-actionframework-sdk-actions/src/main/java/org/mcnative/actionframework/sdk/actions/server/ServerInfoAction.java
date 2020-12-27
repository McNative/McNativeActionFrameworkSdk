package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ServerInfoAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "info";

    private Plugin[] plugins;
    private String[] databaseDrivers;

    public ServerInfoAction(Plugin[] plugins, String[] databaseDrivers) {
        this.plugins = plugins;
        this.databaseDrivers = databaseDrivers;
    }

    public Plugin[] getPlugins() {
        return plugins;
    }

    public String[] getDatabaseDrivers() {
        return databaseDrivers;
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
        plugins = new Plugin[buffer.readInt()];
        for (int i = 0; i < plugins.length; i++) {
            plugins[i] = new Plugin(BufferUtil.readUniqueId(buffer)
                    ,BufferUtil.readString(buffer)
                    ,BufferUtil.readString(buffer));
        }

        databaseDrivers = new String[buffer.readInt()];
        for (int i = 0; i < databaseDrivers.length; i++) {
            databaseDrivers[i] = BufferUtil.readString(buffer);
        }
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(plugins.length);
        for (Plugin plugin : plugins) {
            BufferUtil.writeUniqueId(buffer,plugin.getId() == null ? new UUID(0,0) : plugin.getId());
            BufferUtil.writeString(buffer,plugin.getName());
            BufferUtil.writeString(buffer,plugin.getVersion());
        }
        buffer.writeInt(databaseDrivers.length);
        for (String driver : databaseDrivers) {
            BufferUtil.writeString(buffer,driver);
        }
    }

    public static class Plugin {

        private final UUID uniqueId;//Only McNative
        private final String name;
        private final String version;

        public Plugin(UUID uniqueId, String name, String version) {
            this.uniqueId = uniqueId;
            this.name = name;
            this.version = version;
        }

        public UUID getId() {
            return uniqueId;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }
    }
}
