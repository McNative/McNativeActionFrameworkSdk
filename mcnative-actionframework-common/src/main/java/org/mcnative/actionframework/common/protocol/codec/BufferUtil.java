/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 09.02.19 16:39
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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * This class contains some necessary utils for working with the netty byte buf.
 */
public class BufferUtil {

    /**
     * Write a string with the length to a byte buf.
     *
     * @param buffer The buffer
     * @param content The string for writing
     */
    public static void writeString(ByteBuf buffer, String content){
        writeString(buffer, content, StandardCharsets.UTF_8);
    }

    /**
     * Write a string with the length to a byte buf.
     *
     * <p>Byte construction</p>
     * <p>length (Integer/4 bytes) + string content</p>
     *
     * @param buffer The buffer
     * @param content The string for writing
     * @param charset The charset for writing (We recommend to use UTF-8 or UTF-16)
     */
    public static void writeString(ByteBuf buffer, String content, Charset charset){
        byte[] bytes = content.getBytes(charset);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    /**
     * Rad a string with a length from a byte buf.
     *
     * @param buffer The buffer
     * @return The rode string
     */
    public static String readString(ByteBuf buffer){
        return readString(buffer, StandardCharsets.UTF_8);
    }

    /**
     * Rad a string with a length from a byte buf.
     *
     * @param buffer The buffer
     * @param charset The charset for writing (We recommend to use UTF-8 or UTF-16)
     * @return The rode string
     */
    public static String readString(ByteBuf buffer, Charset charset){
        byte[] bytes = new byte[buffer.readInt()];
        buffer.readBytes(bytes,0,bytes.length);
        return new String(bytes,charset);
    }

    /**
     * Search the next position of a specified byte.
     *
     * @param buffer The buffer
     * @param byte0 The byte for searching
     * @return The index of the byte
     */
    public static int getNextBytePosition(ByteBuf buffer, byte byte0){
        return buffer.forEachByte(value -> value != byte0);
    }

    /**
     * Write a uuid to a byte buf.
     *
     * <p>Byte construction (16 bytes)</p>
     * <p>most significant bits (Long/8 bytes) + least significant bits (Long/8 bytes)</p>
     *
     * @param buffer The buffer
     * @param uuid The uuid to write
     */
    public static void writeUniqueId(ByteBuf buffer, UUID uuid){
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
    }

    /**
     * Rad a uuid from a byte buf
     *
     * @param buffer The buffer
     * @return The rode uuid
     */
    public static UUID readUniqueId(ByteBuf buffer){
        return new UUID(buffer.readLong(),buffer.readLong());
    }

}
