/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 26.04.19 21:33
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

package org.mcnative.actionframework.sdk.common.protocol.packet;

import java.util.UUID;

/**
 * The Prematic cluster data transport contains information about a transaction. It contains
 * the destination, the sender, an unique id of the transaction for identifying this transaction
 * in the network and the packet with the data.
 *
 * @See Transaction principle on Image (Link image)
 *
 */
public class PacketTransport {

    private final UUID transactionId;
    private final Packet packet;

    public PacketTransport(UUID transactionId, Packet packet) {
        this.transactionId = transactionId;
        this.packet = packet;
    }

    /**
     * Get the id of this transaction.
     *
     * <p>Every packet transaction becomes a unique id for identifying this transaction in the
     * network and handling the result. If a packet is sent a s query, the transaction id will be
     * saved an the receiver connection will return a result packet with the same transaction id.
     * The sender receives the result and can now do something with this data.</p>
     *
     * @return The id
     */
    public UUID getTransactionId() {
        return transactionId;
    }

    /**
     * Get the packet of this transaction.
     *
     * @return The packet
     */
    public Packet getPacket() {
        return packet;
    }

}
