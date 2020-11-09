package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;
import com.restsecure.core.mapping.deserialize.DefaultJacksonDeserializer;
import com.restsecure.core.mapping.deserialize.Deserializer;

public class DeserializerConfig extends BaseConfig<Deserializer> {
    @Override
    public void initDefault() {
        value = new DefaultJacksonDeserializer();
    }
}
