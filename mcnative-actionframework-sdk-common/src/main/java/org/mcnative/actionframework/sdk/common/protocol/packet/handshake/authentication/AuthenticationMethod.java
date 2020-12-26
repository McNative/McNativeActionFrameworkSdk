package org.mcnative.actionframework.sdk.common.protocol.packet.handshake.authentication;

import net.pretronic.libraries.utility.reflect.UnsafeInstanceCreator;

public enum AuthenticationMethod {

    NETWORK_KEY(KeyAuthentication.class);

    AuthenticationMethod(Class<? extends Authentication> clazz) {
        this.clazz = clazz;
    }

    private final Class<? extends Authentication> clazz;


    public Class<? extends Authentication> getAuthenticationClass() {
        return clazz;
    }

    public byte getId(){
        return (byte) ordinal();
    }

    public static AuthenticationMethod of(byte id){
        for (AuthenticationMethod value : values()) {
            if(value.getId() == id) return value;
        }
        throw new UnsupportedOperationException("Client type is not supported");
    }

    public static Authentication create(AuthenticationMethod method ){
        return UnsafeInstanceCreator.newInstance(method.getAuthenticationClass());
    }

    public static Authentication create(byte id){
        return create(of(id));
    }

}
