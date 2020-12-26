/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 09.02.19 12:08
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
import io.netty.handler.codec.MessageToByteEncoder;
import net.pretronic.libraries.logging.PretronicLogger;
import org.mcnative.actionframework.common.protocol.packet.PacketTransport;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

public class MAFProtocolEncoder extends MessageToByteEncoder<PacketTransport> {

    private final PretronicLogger logger;
    private final SerializerRegistry serializerRegistry;

    public MAFProtocolEncoder(PretronicLogger logger, SerializerRegistry serializerRegistry) {
        this.logger = logger;
        this.serializerRegistry = serializerRegistry;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketTransport transport, ByteBuf buffer) {
        try{
            BufferUtil.writeUniqueId(buffer,transport.getTransactionId());
            buffer.writeByte(transport.getPacket().getId());
            transport.getPacket().writeBody(this.serializerRegistry,buffer);
        }catch (Exception exception){
            exception.printStackTrace();
            buffer.clear();
            this.logger.error(exception,"An exception in protocol encoder occurred (TransactionId: {} PacketId: {})"
                    ,transport.getTransactionId(),transport.getPacket().getId());
        }
    }
}
