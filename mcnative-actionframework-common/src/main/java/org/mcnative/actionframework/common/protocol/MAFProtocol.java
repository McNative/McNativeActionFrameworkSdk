/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 26.04.19 21:32
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

package org.mcnative.actionframework.common.protocol;

import io.netty.channel.Channel;
import net.pretronic.libraries.logging.PretronicLogger;
import org.mcnative.actionframework.common.protocol.codec.LengthDeserializer;
import org.mcnative.actionframework.common.protocol.codec.LengthSerializer;
import org.mcnative.actionframework.common.protocol.codec.MAFProtocolDecoder;
import org.mcnative.actionframework.common.protocol.codec.MAFProtocolEncoder;
import org.mcnative.actionframework.common.serialisation.SerializerRegistry;

/**
 * The Prematic cluster data transport protocol (PCDTP) is used for transporting universal data over a cluster network.
 * The protocol is separated in a header and boy part. You are able to write your own body content
 * (@See {@link net.prematic.networking.libraries.protocol.packet.Packet}
 *
 * <p>Protocol construction</p>
 * <p>Header[DestinationId (16 bytes) + SenderId (16 bytes) + TransactionId + (16 bytes) + PacketIdentifierClass (Individual length)]</p>
 * <p>Body[Individual length]</p>
 *
 * <p>This protocol is designed for sending packet over different instances to the right destination.
 * Usually a packet is sent to a node and the node will forward the packet to the right destination connection.</p>
 *
 * <p></p>
 *
 * <p>This class contains only the channel initializer.</p>
 */
public class MAFProtocol {

    /**
     * Initializer a channel with all handlers for reading the prematic cluster data transport protocol.
     *
     * @param channel The channel
     * @param logger The logger of the instance
     * @param serializerRegistry The serialize registry
     * @param outgoingSenderInitializer The sender initializer
     */
    public static void initializeChannel(Channel channel, PretronicLogger logger, SerializerRegistry serializerRegistry){

        channel.pipeline().addLast(new LengthDeserializer()
                ,new MAFProtocolDecoder(logger,serializerRegistry)
                ,new LengthSerializer()
                ,new MAFProtocolEncoder(logger,serializerRegistry));
        /*
        channel.pipeline().addLast(new LengthDeserializer()
                ,new MAFProtocolDecoder(logger,serializerRegistry)
                ,new LengthSerializer()
                ,new MAFProtocolEncoder(logger,serializerRegistry));
         */
    }

    /**
     * Create new netty channel initializer for handling prematic cluster data transport packets.
     */
    public static PCDTPChannelInitializer newChannelInitializer(PretronicLogger logger, SerializerRegistry serializerRegistry){
        return new PCDTPChannelInitializer(logger,serializerRegistry);
    }

    private static class PCDTPChannelInitializer extends io.netty.channel.ChannelInitializer<Channel> {

        private final PretronicLogger logger;
        private final SerializerRegistry serializerRegistry;

        PCDTPChannelInitializer(PretronicLogger logger, SerializerRegistry serializerRegistry) {
            this.logger = logger;
            this.serializerRegistry = serializerRegistry;
        }

        @Override
        protected void initChannel(Channel channel) throws Exception {
            initializeChannel(channel,logger,serializerRegistry);
        }
    }

}
