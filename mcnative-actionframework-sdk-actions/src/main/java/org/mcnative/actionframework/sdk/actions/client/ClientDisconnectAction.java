package org.mcnative.actionframework.sdk.actions.client;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.sdk.common.action.MAFAction;

public class ClientDisconnectAction implements MAFAction {

    public static final String NAMESPACE = "cln";
    public static final String NAME = "disconnect";

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
