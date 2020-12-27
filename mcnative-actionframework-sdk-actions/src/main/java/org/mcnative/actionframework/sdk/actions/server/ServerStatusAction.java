package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;

public class ServerStatusAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "status";

    private int maximumPlayerCount;
    private float tps;
    private int usedMemory;
    private float cpuUsage;

    public ServerStatusAction(int maximumPlayerCount, float tps, int usedMemory, float cpuUsage) {
        this.maximumPlayerCount = maximumPlayerCount;
        this.tps = tps;
        this.usedMemory = usedMemory;
        this.cpuUsage = cpuUsage;
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
    public void read(int version, ByteBuf buffer) {
        this.maximumPlayerCount = buffer.readInt();
        this.tps = buffer.readFloat();
        this.usedMemory = buffer.readInt();
        this.cpuUsage = buffer.readFloat();
    }

    @Override
    public void write(ByteBuf buffer) {
        this.maximumPlayerCount = buffer.readInt();
        this.tps = buffer.readFloat();
        this.usedMemory = buffer.readInt();
        this.cpuUsage = buffer.readFloat();
    }

    public int getMaximumPlayerCount() {
        return maximumPlayerCount;
    }

    public float getTps() {
        return tps;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }
}
