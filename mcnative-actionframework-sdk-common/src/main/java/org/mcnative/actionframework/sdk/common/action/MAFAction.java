package org.mcnative.actionframework.sdk.common.action;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public interface MAFAction {

    String getNamespace();

    String getName();//.server.

    default byte getVersion(){
        return 1;
    }

    void read(int version,ByteBuf buffer);

    void write(ByteBuf buffer);


    default void readAction(byte[] content){
        ByteBuf buffer = Unpooled.wrappedBuffer(content);
        readAction(buffer);
        buffer.release();
    }

    default void readAction(ByteBuf buffer){
        read(buffer.readInt(),buffer);
    }

    default void writeAction(ByteBuf buffer){
        buffer.writeInt(getVersion());
        write(buffer);
    }

    default void validate(){
        //not implemented
    }

}
