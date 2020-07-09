package com.restsecure.core.deserialize;

import com.restsecure.core.configuration.Config;
import lombok.Getter;

public class DeserializeConfig implements Config {

    @Getter
    private Deserializer deserializer;

    public DeserializeConfig setDeserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
        return this;
    }

    @Override
    public void reset() {
        deserializer = new DefaultJacksonJsonDeserializer();
    }
}
