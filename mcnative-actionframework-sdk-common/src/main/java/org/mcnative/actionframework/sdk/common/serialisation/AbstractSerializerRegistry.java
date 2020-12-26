/*
 * (C) Copyright 2019 The PrematicNetworking Project
 *
 * @author Davide Wietlisbach
 * @since 20.04.19 20:51
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is a abstract implementation of the network serializer.
 */
public class AbstractSerializerRegistry implements SerializerRegistry {

    private final Map<Class<?>, Serializer<?>> serializers;

    public AbstractSerializerRegistry() {
        this.serializers = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, O extends T> Serializer<O> getSerializer(Class<T> forClass) {
        Serializer<?> serializer = serializers.get(forClass);
        if(serializer == null) throw new IllegalArgumentException("No serializer for "+forClass+" found.");
        return (Serializer<O>) serializer;
    }

    @Override
    public <T, O extends T> void registerSerializer(Class<T> forClass, Serializer<O> serializer) {
        serializers.put(forClass,serializer);
    }

    @Override
    public void unregisterSerializer(Class<?> forClass) {
        this.serializers.remove(forClass);
    }

    @Override
    public void unregisterSerializer(Serializer<?> serializer) {
        this.serializers.values().remove(serializer);
    }
}
