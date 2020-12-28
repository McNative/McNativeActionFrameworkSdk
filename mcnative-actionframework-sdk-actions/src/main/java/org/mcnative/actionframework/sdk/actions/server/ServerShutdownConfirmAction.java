package org.mcnative.actionframework.sdk.actions.server;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;

public class ServerShutdownConfirmAction implements MAFAction {

    public static final String NAMESPACE = "srv";
    public static final String NAME = "shutConf";

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
        //No content
    }

    @Override
    public void write(ByteBuf buffer) {
        //No content
    }
}
