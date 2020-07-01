package com.restsecure.components.deserialize;

import com.restsecure.components.deserialize.DefaultJacksonJsonDeserializer;
import com.restsecure.components.deserialize.DeserializeConfig;
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
