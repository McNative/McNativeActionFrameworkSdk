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

/**
 * The serializer registry contains and manages network serializers on a nework instance (client or node).
 *
 * <p>Network serializers are used for serialising objects (Usually interfaces) which are sent to other network instances.
 *      * The serializer must always be registered on both network instances (Sender and receiver).</p>
 */
public interface SerializerRegistry {

    /**
     * Get a registered network serializer.
     *
     * @param forClass The class of the object for serializing
     * @param <T> The class of the object for serialising
     * @param <O> The class in the serializer, must extend or implement from the T class
     * @return The network serializer for this network type
     */
    <T, O extends T> Serializer<O> getSerializer(Class<T> forClass);

    /**
     * Register a new network serializer for a object .
     *
     * @param forClass The class of the object for serialising
     * @param serializer The serializer
     * @param <T> The class of the object for serialising
     * @param <O> The class in the serializer, must extend or implement from the T class
     */
    <T, O extends T> void registerSerializer(Class<T> forClass, Serializer<O> serializer);

    /**
     * Unregister a registered serializer from this registry..
     *
     * @param forClass The class of the object to serialize and deserialize
     */
    void unregisterSerializer(Class<?> forClass);

    /**
     * Unregister a registered serializer from this registry.
     *
     * @param serializer The registered serializer
     */
    void unregisterSerializer(Serializer<?> serializer);
}
