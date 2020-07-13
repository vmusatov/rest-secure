package com.restsecure.core.mapping.serialize;

import com.restsecure.core.configuration.Config;
import lombok.Getter;

public class SerializeConfig implements Config {

    @Getter
    private Serializer serializer;

    public SerializeConfig setSerializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public void reset() {
        this.serializer = new DefaultJacksonSerializer();
    }
}
