/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 20.04.19 20:48
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

package org.mcnative.actionframework.common.serialisation;

import io.netty.buffer.ByteBuf;

/**
 * Network serializers are used for serialising objects (Usually interfaces) which are sent to other network instances.
 * The serializer must always be registered on both network instances (Sender and receiver).
 *
 * <p>External serializer are usually used for serialising interfaces or object with a different types.</p>
 *
 * @param <T> The object for serialising
 */
public interface Serializer<T> {

    /**
     * Serialize a object to a byte buf
     *
     * @param buffer The buffer for writing the byte content
     * @param data The object to serialize
     */
    void serialize(ByteBuf buffer, T data);

    /**
     * Deserialize the object from a byte buf.
     *
     * @param buffer The buffer with the byte content
     * @return The created object
     */
    T deserialize(ByteBuf buffer);
}
