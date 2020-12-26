package org.mcnative.actionframework.common.protocol.packet;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.common.action.MAFAction;
import org.mcnative.actionframework.common.protocol.codec.BufferUtil;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

public class ActionPacket implements Packet{

    public static byte IDENTIFIER = 2;

    private String namespace;
    private String name;

    private byte[] content;
    private MAFAction action;

    //private final MAFAction action;

    public ActionPacket(MAFAction action) {
        this.namespace = action.getNamespace();
        this.name = action.getName();
        this.action = action;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }

    public MAFAction getAction() {
        if(action == null){
            //@Todo create action
        }
        return action;
    }

    @Override
    public byte getId() {
        return IDENTIFIER;
    }

    @Override
    public void writeBody(SerializerRegistry registry, ByteBuf buffer) {
        BufferUtil.writeString(buffer,namespace);
        BufferUtil.writeString(buffer,name);

        if(action == null) buffer.writeBytes(content);
        else action.write(buffer);
    }

    @Override
    public void readBody(SerializerRegistry registry, ByteBuf buffer) {
        this.namespace = BufferUtil.readString(buffer);
        this.name = BufferUtil.readString(buffer);

        this.content = new byte[buffer.readableBytes()];
        buffer.getBytes(buffer.readerIndex(), this.content);
    }
}
