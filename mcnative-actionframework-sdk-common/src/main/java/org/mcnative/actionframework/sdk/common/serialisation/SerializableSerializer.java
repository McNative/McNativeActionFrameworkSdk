/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 08.07.19 11:49
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

package org.mcnative.actionframework.sdk.common.serialisation;

import io.netty.buffer.ByteBuf;

/**
 * This is a implementation of the network serializer for serialising serializable objects (It is like a translation).
 *
 * @param <T> The class of the object for serialising
 */
public class SerializableSerializer<T extends Serializable> implements Serializer<T> {

    private final Class<T> serializeClass;

    public SerializableSerializer(Class<T> serializeClass) {
        this.serializeClass = serializeClass;
    }

    @Override
    public void serialize(ByteBuf buffer, T data) {
        data.write(buffer);
    }

    @Override
    public T deserialize(ByteBuf buffer) {
        return Serializable.create(serializeClass,buffer);
    }

    public static <O extends Serializable> SerializableSerializer<O> create(Class<O> serializeClass){
        return new SerializableSerializer<>(serializeClass);
    }
}
