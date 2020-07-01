package com.restsecure.components.deserialize;

import com.restsecure.components.configuration.Config;
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
