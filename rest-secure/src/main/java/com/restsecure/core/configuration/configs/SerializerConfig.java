package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;
import com.restsecure.core.mapping.serialize.DefaultJacksonSerializer;
import com.restsecure.core.mapping.serialize.Serializer;

public class SerializerConfig extends BaseConfig<Serializer> {
    @Override
    public void initDefault() {
        value = new DefaultJacksonSerializer();
    }
}
