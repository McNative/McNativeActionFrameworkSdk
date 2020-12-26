package org.mcnative.actionframework.sdk.common.action;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public interface MAFAction {

    String getNamespace();

    String getName();//.server.

    default void read(byte[] content){
        ByteBuf buffer = Unpooled.wrappedBuffer(content);
        read(buffer);
        buffer.release();
    }

    void read(ByteBuf buffer);

    void write(ByteBuf buffer);

    default void validate(){

    }

}
