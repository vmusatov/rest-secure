package com.restsecure.core.config;

import com.restsecure.BaseTest;
import com.restsecure.MockServer;
import com.restsecure.core.configuration.configs.OverwriteAllHeadersConfig;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecImpl;
import com.restsecure.core.util.MultiKeyMap;
import org.testng.annotations.Test;

import static com.restsecure.Configs.overwriteAllHeaders;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OverwriteAllHeadersConfigTest extends BaseTest {

    @Test
    public void setValueTest() {
        OverwriteAllHeadersConfig config = new OverwriteAllHeadersConfig();
        config.setValue(true);

        assertThat(config.getValue(), equalTo(true));
    }

    @Test
    public void getValueTest() {
        OverwriteAllHeadersConfig config = new OverwriteAllHeadersConfig();
        assertThat(config.getValue(), equalTo(null));

        config.setValue(true);
        assertThat(config.getValue(), equalTo(true));
    }

    @Test
    public void initDefaultTest() {
        OverwriteAllHeadersConfig config = new OverwriteAllHeadersConfig();
        config.setValue(true);
        config.initDefault();

        assertThat(config.getValue(), equalTo(false));
    }

    @Test
    public void applyConfigTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url(MockServer.GET_PATH)
                .method(RequestMethod.GET)
                .config(overwriteAllHeaders(true))
                .header("one", "one value1")
                .header("two", "two value1")
                .header("one", "one value2")
                .header("two", "one value2")
                .header("three", "three value")
                .header("two", "two value3");

        RequestContext context = new RequestContext(spec);
        processRequest(context);

        MultiKeyMap<String, Object> headers = context.getRequestSpec().getHeaders();

        assertThat(headers.size(), equalTo(3));
        assertThat(headers.getFirst("one"), equalTo("one value2"));
        assertThat(headers.getFirst("two"), equalTo("two value3"));
        assertThat(headers.getFirst("three"), equalTo("three value"));
    }
}
