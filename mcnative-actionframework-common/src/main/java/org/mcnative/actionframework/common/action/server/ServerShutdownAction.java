package org.mcnative.actionframework.common.action.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.common.action.MAFAction;

public class ServerShutdownAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "shutdown";

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
        //No content
    }

    @Override
    public void write(ByteBuf buffer) {
        //No content
    }
}
