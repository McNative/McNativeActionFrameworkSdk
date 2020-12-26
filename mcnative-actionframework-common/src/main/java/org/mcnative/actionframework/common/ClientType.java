package org.mcnative.actionframework.common;

public enum ClientType {

    GENERIC();

    public byte getId(){
        return (byte) ordinal();
    }

    public static ClientType of(byte id){
        for (ClientType value : values()) {
            if(value.getId() == id) return value;
        }
        throw new UnsupportedOperationException("Client type is not supported");
    }

}
