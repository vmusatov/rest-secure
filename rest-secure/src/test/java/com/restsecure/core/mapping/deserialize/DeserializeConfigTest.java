package com.restsecure.core.mapping.deserialize;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeserializeConfigTest {

    @Test
    public void setDeserializerTest() {
        DeserializeConfig config = new DeserializeConfig();
        DefaultJacksonDeserializer deserializer = new DefaultJacksonDeserializer();
        config.setDeserializer(deserializer);

        assertThat(config.getDeserializer(), equalTo(deserializer));
    }
}
