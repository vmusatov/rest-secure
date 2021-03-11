package com.restsecure.core.config;

import com.restsecure.MockServer;
import com.restsecure.RestSecure;
import com.restsecure.core.configuration.configs.OverwriteHeadersConfig;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.RequestFactory;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.util.MultiKeyMap;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.restsecure.Configs.overwriteHeaders;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OverwriteHeadersConfigTest {

    @Test
    public void setValueTest() {
        OverwriteHeadersConfig config = new OverwriteHeadersConfig();
        config.setValue(Arrays.asList("one", "two, three"));

        assertThat(config.getValue(), equalTo(Arrays.asList("one", "two, three")));
    }

    @Test
    public void getValueTest() {
        OverwriteHeadersConfig config = new OverwriteHeadersConfig();
        assertThat(config.getValue(), equalTo(null));

        config.setValue(Arrays.asList("one", "two, three"));
        assertThat(config.getValue(), equalTo(Arrays.asList("one", "two, three")));
    }

    @Test
    public void initDefaultTest() {
        OverwriteHeadersConfig config = new OverwriteHeadersConfig();
        config.setValue(Arrays.asList("one", "two, three"));
        config.initDefault();

        assertThat(config.getValue(), equalTo(Collections.emptyList()));
    }

    @Test
    public void applyConfigTest() {
        RequestSpec spec = RestSecure.get(MockServer.GET_PATH)
                .config(overwriteHeaders("one"))
                .header("one", "one value1")
                .header("two", "two value1")
                .header("one", "one value2")
                .header("two", "two value2")
                .header("three", "three value");

        RequestContext context = new RequestContext(spec);
        RequestFactory.createRequest(context);

        MultiKeyMap<String, Object> headers = context.getRequestSpec().getHeaders();

        assertThat(headers.size(), equalTo(4));
        assertThat(headers.getFirst("one"), equalTo("one value2"));
        assertThat(headers.getFirst("two"), equalTo("two value1"));
        assertThat(headers.getLast("two"), equalTo("two value2"));
        assertThat(headers.getFirst("three"), equalTo("three value"));
    }
}
