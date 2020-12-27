package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ServerStartupAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "startup";

    private InetSocketAddress address;
    private String platformName;
    private String platformVersion;
    private boolean platformProxy;
    private String networkTechnology;
    private int protocolVersion;
    private int[] joinAbleProtocolVersions;
    private int mcnativeBuildNumber;
    private Plugin[] plugins;
    private String[] databaseDrivers;

    private String operatingSystem;
    private String osArchitecture;
    private String javaVersion;
    private String deviceId;
    private int maximumMemory;
    private int availableCores;

    public ServerStartupAction(InetSocketAddress address, String platformName, String platformVersion, boolean platformProxy
            , String networkTechnology, int protocolVersion, int[] joinAbleProtocolVersions, int mcnativeBuildNumber
            , Plugin[] plugins, String[] databaseDrivers) {
        this.address = address;
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.platformProxy = platformProxy;
        this.networkTechnology = networkTechnology;
        this.protocolVersion = protocolVersion;
        this.joinAbleProtocolVersions = joinAbleProtocolVersions;
        this.mcnativeBuildNumber = mcnativeBuildNumber;
        this.plugins = plugins;
        this.databaseDrivers = databaseDrivers;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public String getPlatformName() {
        return platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public boolean isPlatformProxy() {
        return platformProxy;
    }

    public String getNetworkTechnology() {
        return networkTechnology;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public int[] getJoinAbleProtocolVersions() {
        return joinAbleProtocolVersions;
    }

    public int getMcnativeBuildNumber() {
        return mcnativeBuildNumber;
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
        address = new InetSocketAddress(BufferUtil.readString(buffer),buffer.readInt());
        platformName = BufferUtil.readString(buffer);
        platformVersion = BufferUtil.readString(buffer);
        platformProxy = buffer.readBoolean();
        networkTechnology = BufferUtil.readString(buffer);
        protocolVersion = buffer.readInt();

        joinAbleProtocolVersions = new int[buffer.readInt()];
        for (int i = 0; i < joinAbleProtocolVersions.length; i++) {
            joinAbleProtocolVersions[i] = buffer.readInt();
        }

        mcnativeBuildNumber = buffer.readInt();

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
        BufferUtil.writeString(buffer,address.getHostName());
        buffer.writeInt(address.getPort());
        BufferUtil.writeString(buffer,platformName);
        BufferUtil.writeString(buffer,platformVersion);
        buffer.writeBoolean(platformProxy);
        BufferUtil.writeString(buffer,networkTechnology);
        buffer.writeInt(protocolVersion);
        buffer.writeInt(joinAbleProtocolVersions.length);
        for (int version : joinAbleProtocolVersions){
            buffer.writeInt(version);
        }
        buffer.writeInt(mcnativeBuildNumber);
        buffer.writeInt(plugins.length);
        for (Plugin plugin : plugins) {
            BufferUtil.writeUniqueId(buffer,plugin.getId());
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
