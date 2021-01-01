package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ServerStartupAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "startup";

    private String serverName;
    private InetSocketAddress address;
    private String serverGroup;
    private String platformName;
    private String platformVersion;
    private boolean platformProxy;
    private String networkTechnology;
    private int protocolVersion;
    private int[] joinAbleProtocolVersions;
    private int mcnativeBuildNumber;

    private String operatingSystem;
    private String osArchitecture;
    private String javaVersion;
    private String deviceId;
    private int maximumMemory;
    private int availableCores;

    public ServerStartupAction(String serverName, InetSocketAddress address, String serverGroup, String platformName, String platformVersion
            , boolean platformProxy, String networkTechnology, int protocolVersion, int[] joinAbleProtocolVersions
            , int mcnativeBuildNumber, String operatingSystem
            , String osArchitecture, String javaVersion, String deviceId, int maximumMemory, int availableCores) {
        this.serverName = serverName;
        this.address = address;
        this.serverGroup = serverGroup;
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.platformProxy = platformProxy;
        this.networkTechnology = networkTechnology;
        this.protocolVersion = protocolVersion;
        this.joinAbleProtocolVersions = joinAbleProtocolVersions;
        this.mcnativeBuildNumber = mcnativeBuildNumber;
        this.operatingSystem = operatingSystem;
        this.osArchitecture = osArchitecture;
        this.javaVersion = javaVersion;
        this.deviceId = deviceId;
        this.maximumMemory = maximumMemory;
        this.availableCores = availableCores;
    }

    public static String getNAMESPACE() {
        return NAMESPACE;
    }

    public static String getNAME() {
        return NAME;
    }

    public String getServerName() {
        return serverName;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public String getServerGroup() {
        return serverGroup;
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

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getOsArchitecture() {
        return osArchitecture;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getMaximumMemory() {
        return maximumMemory;
    }

    public int getAvailableCores() {
        return availableCores;
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
        this.serverName = BufferUtil.readString(buffer);
        address = new InetSocketAddress(BufferUtil.readString(buffer),buffer.readInt());
        serverGroup = BufferUtil.readString(buffer);
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

        this.operatingSystem = BufferUtil.readString(buffer);
        this.osArchitecture = BufferUtil.readString(buffer);
        this.javaVersion = BufferUtil.readString(buffer);
        this.deviceId = BufferUtil.readString(buffer);
        this.maximumMemory = buffer.readInt();
        this.availableCores = buffer.readInt();
    }

    @Override
    public void write(ByteBuf buffer) {
        BufferUtil.writeString(buffer, this.serverName);
        BufferUtil.writeString(buffer,address.getHostName());
        buffer.writeInt(address.getPort());
        BufferUtil.writeString(buffer,serverGroup);
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
        BufferUtil.writeString(buffer,this.operatingSystem);
        BufferUtil.writeString(buffer,this.osArchitecture);
        BufferUtil.writeString(buffer,this.javaVersion);
        BufferUtil.writeString(buffer,this.deviceId);
        buffer.writeInt(this.maximumMemory);
        buffer.writeInt(this.availableCores);
    }
}
