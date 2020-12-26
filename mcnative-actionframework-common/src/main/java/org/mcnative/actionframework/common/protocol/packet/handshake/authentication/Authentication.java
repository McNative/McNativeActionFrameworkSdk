package org.mcnative.actionframework.common.protocol.packet.handshake.authentication;

import org.mcnative.actionframework.common.serialisation.Serializable;

public interface Authentication extends Serializable {

    AuthenticationMethod getMethod();

}
