/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 09.02.19 12:07
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

package org.mcnative.actionframework.common.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.pretronic.libraries.logging.PretronicLogger;
import net.pretronic.libraries.utility.reflect.UnsafeInstanceCreator;
import org.mcnative.actionframework.common.protocol.packet.Packet;
import org.mcnative.actionframework.common.protocol.packet.PacketRegistry;
import org.mcnative.actionframework.common.protocol.packet.PacketTransport;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

import java.util.List;
import java.util.UUID;

public class MAFProtocolDecoder extends ByteToMessageDecoder {

    private final PretronicLogger logger;
    private final SerializerRegistry serializerRegistry;

    public MAFProtocolDecoder(PretronicLogger logger, SerializerRegistry serializerRegistry) {
        this.logger = logger;
        this.serializerRegistry = serializerRegistry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        byte id = 0;
        UUID transactionId = null;
        try{
            transactionId = BufferUtil.readUniqueId(buffer);
            id = buffer.readByte();

            Class<? extends Packet> packetClass = PacketRegistry.getPacketClass(id);
            Packet packet = UnsafeInstanceCreator.newInstance(packetClass);
            packet.readBody(this.serializerRegistry,buffer);
            out.add(new PacketTransport(transactionId,packet));
            buffer.clear();
        }catch (Exception exception){
            this.logger.error(exception,"An exception in protocol encoder occurred (TransactionId: {} PacketId: {})"
                    ,transactionId,id);
        }
    }
}
