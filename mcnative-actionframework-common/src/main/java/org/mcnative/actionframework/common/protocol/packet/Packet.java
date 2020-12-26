/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 10.02.19 17:43
 * @Website %web%
 *
 * The PrematicNetworking Project is under the Apache License, version 2.0 (the "License");
 * you may not use this io except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.mcnative.actionframework.common.protocol.packet;

import io.netty.buffer.ByteBuf;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

/**
 * The Prematic cluster data transport packet is a universal packet for transporting
 * data over a cluster network. It is possible to send data from a client through different
 * nodes to another client. The packet will search the way through the network and arrive at the right
 * destination (client or node).
 *
 * <p>The packet represents the body of the the prematic cluster data transport protocol (PCDTP).
 * You are able to write your own byte content and use the protocol for every kind of data.
 * It is possible to transport simple string information or large files.</p>
 *
 * <p>The packets are sent over a network connection to another instance. with a packet listener
 * you can handle received packets.</p>
 *
 * <p>The 00000000-0000-0000-0000-000000000000 uuid is used as destination for broadcasting
 * packets to all instances in the network.</p>
 *
 * <p>@See  fore more information about the protocol construction.</p>
 */
public interface Packet {

    byte getId();

    /**
     *
     * Write the content of this packet to the buffer.
     *
     * @param registry The serialize registry with object serializers
     * @param buffer The buffer for writing the byte content
     */
    void writeBody(SerializerRegistry registry, ByteBuf buffer);

    /**
     * Rad the boy from the buffer.
     *
     * <p>Before the write method is called, the packet was created with unsafe reflections.</p>
     *
     * @param registry The serialize registry with object serializers
     * @param buffer The buffer with the byte content
     */
    void readBody(SerializerRegistry registry,ByteBuf buffer);

}
