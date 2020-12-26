/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 20.04.19 22:09
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
import net.pretronic.libraries.utility.reflect.UnsafeInstanceCreator;

/**
 * Every object that contains this class is serializable in the network.
 *
 * <p>It is possible to read and write a object as byte content to byte buf. Before the read method
 * is called, the object was created via unsafe reflections.</p>
 *
 */
public interface Serializable {

    /**
     * Write this object to a byte buf.
     *
     * @param buffer The buffer for writing the byte content
     */
    void write(ByteBuf buffer);

    /**
     * Rad the object from a byte buf.
     *
     * @param buffer The buffer with the byte content
     */
    void read(ByteBuf buffer);

    /**
     * Check if a object is network serializable.
     * @param clazz The class to check
     *
     * @return If this class is serializable
     */
    static boolean isSerializeAble(Class<?> clazz){
        return Serializable.class.isAssignableFrom(clazz);
    }

    /**
     * Creat and deserialize a object from a buffer.
     *
     * @param clazz The class of the object (No interface or abstract class).
     * @param buffer The buffer with the byte content
     * @param <T> The class of the object to deserialize
     * @return The deserialized object
     */
    static <T extends Serializable> T create(Class<T> clazz, ByteBuf buffer){
        if(!isSerializeAble(clazz)) throw new IllegalArgumentException("This object is not serialize able.");
        if(clazz.isInterface()) throw new IllegalArgumentException("Cannot serialize interfaces, for interface serialisation use the serializer registry.");
        try {
            T object = UnsafeInstanceCreator.newInstance(clazz);
            object.read(buffer);
            return object;
        } catch (Exception exception) {
            throw new UnsupportedOperationException("Could not create a new object of "+clazz);
        }
    }

}
