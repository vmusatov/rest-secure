package com.restsecure.configuration;

import com.restsecure.deserialize.DefaultJacksonJsonDeserializer;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeserializeConfigTest {

    @Test
    public void setDeserializerTest() {
        DeserializeConfig config = new DeserializeConfig();
        DefaultJacksonJsonDeserializer deserializer = new DefaultJacksonJsonDeserializer();
        config.setDeserializer(deserializer);

        assertThat(config.getDeserializer(), equalTo(deserializer));
    }
}
