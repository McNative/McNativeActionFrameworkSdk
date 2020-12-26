/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 10.02.19 22:51
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

package org.mcnative.actionframework.sdk.common.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * This deserializer is for converting the minimised byte content in the normal byte content.
 */
public class LengthDeserializer extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		in.markReaderIndex();
		byte[] lengthBytes = new byte[3];
		for(int i = 0; i < 3; i++){
			if(!in.isReadable()) {
				in.resetReaderIndex();
				return;
			}
			lengthBytes[i] = in.readByte();
			if(lengthBytes[i] >= 0) {
				ByteBuf buffer = Unpooled.wrappedBuffer(lengthBytes);
				try {
					int length = readInt(buffer);
					if(in.readableBytes() < length){
						in.resetReaderIndex();
						return;
					}
					out.add(in.readBytes(length));
				}finally{
					buffer.release();
				}
				return;
			}
		}
	}

	private int readInt(ByteBuf byteBuf){
		int number = 0;
		int round = 0;
		byte current;
		do{
			current = byteBuf.readByte();
			number |= (current & 127) << round++ * 7;
			if(round > 5) throw new IllegalArgumentException("Internal problem");
		}while((current & 128) == 128);
		return number;
	}
}
