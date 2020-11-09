package com.restsecure.core.mapping.deserialize;

import com.restsecure.core.configuration.configs.DeserializerConfig;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeserializeConfigTest {

    @Test
    public void setDeserializerTest() {
        DefaultJacksonDeserializer deserializer = new DefaultJacksonDeserializer();
        DeserializerConfig config = new DeserializerConfig();
        config.setValue(deserializer);

        assertThat(config.getValue(), equalTo(deserializer));
    }
}
