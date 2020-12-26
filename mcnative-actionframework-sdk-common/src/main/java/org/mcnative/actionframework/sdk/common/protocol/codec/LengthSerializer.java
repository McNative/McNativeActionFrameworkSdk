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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * This handler is for minimising the byte content
 */
public class LengthSerializer extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object object, ByteBuf out){
		if(object instanceof ByteBuf){
			ByteBuf in = (ByteBuf)object;

			int readable = in.readableBytes();
			int length = getIntSize(readable);

			if(length > 3) throw new IllegalArgumentException();
			out.ensureWritable(length+readable);
			writeInt(readable, out);
			out.writeBytes(in, in.readerIndex(),readable);
		}
	}

	private int getIntSize(int value) {
		if((value & -128) == 0) return 1;
		else if((value & -16384) == 0) return 2;
		else if((value & -2097152) == 0) return 3;
		else if((value & -268435456) == 0) return 4;
		return 5;
	}

	private void writeInt(int number, ByteBuf byteBuf) {
		while((number & -128) != 0){
			byteBuf.writeByte(number & 127 | 128);
			number >>>= 7;
		}
		byteBuf.writeByte(number);
	}
}
