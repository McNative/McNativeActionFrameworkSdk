package org.mcnative.actionframework.sdk.common.protocol.packet.handshake.authentication;

import org.mcnative.actionframework.sdk.common.serialisation.Serializable;

public interface Authentication extends Serializable {

    AuthenticationMethod getMethod();

}
