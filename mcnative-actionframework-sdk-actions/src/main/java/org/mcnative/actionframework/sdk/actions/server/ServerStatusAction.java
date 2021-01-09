package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import net.pretronic.libraries.utility.Validate;
import org.mcnative.actionframework.sdk.common.action.MAFAction;
import org.mcnative.actionframework.sdk.common.protocol.codec.BufferUtil;

public class ServerStatusAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "status";

    private int maximumPlayerCount;
    private float[] recentTps;
    private int usedMemory;
    private float cpuUsage;

    public ServerStatusAction(int maximumPlayerCount, float[] recentTps, int usedMemory, float cpuUsage) {
        Validate.isTrue(recentTps.length == 3, "Recent tps length must be 3");
        this.maximumPlayerCount = maximumPlayerCount;
        this.recentTps = recentTps;
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

        this.recentTps = new float[3];
        this.recentTps[0] = buffer.readFloat();
        this.recentTps[1] = buffer.readFloat();
        this.recentTps[2] = buffer.readFloat();

        this.usedMemory = buffer.readInt();
        this.cpuUsage = buffer.readFloat();
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(this.maximumPlayerCount);

        buffer.writeFloat(this.recentTps[0]);
        buffer.writeFloat(this.recentTps[1]);
        buffer.writeFloat(this.recentTps[2]);

        buffer.writeInt(this.usedMemory);
        buffer.writeFloat(this.cpuUsage);
    }

    public int getMaximumPlayerCount() {
        return maximumPlayerCount;
    }

    public float[] getRecentTps() {
        return recentTps;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }
}
