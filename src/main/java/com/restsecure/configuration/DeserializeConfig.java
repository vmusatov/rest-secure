package com.restsecure.configuration;

import com.restsecure.deserialize.DefaultJacksonJsonDeserializer;
import com.restsecure.deserialize.Deserializer;
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
