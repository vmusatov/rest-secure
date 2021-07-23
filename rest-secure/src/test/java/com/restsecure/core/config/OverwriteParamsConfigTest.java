package com.restsecure.core.config;

import com.restsecure.BaseTest;
import com.restsecure.MockServer;
import com.restsecure.core.configuration.configs.OverwriteParamsConfig;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecImpl;
import com.restsecure.core.util.MultiKeyMap;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.restsecure.Configs.overwriteParams;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OverwriteParamsConfigTest extends BaseTest {

    @Test
    public void setValueTest() {
        OverwriteParamsConfig config = new OverwriteParamsConfig();
        config.setValue(Arrays.asList("one", "two, three"));

        assertThat(config.getValue(), equalTo(Arrays.asList("one", "two, three")));
    }

    @Test
    public void getValueTest() {
        OverwriteParamsConfig config = new OverwriteParamsConfig();
        assertThat(config.getValue(), equalTo(null));

        config.setValue(Arrays.asList("one", "two, three"));
        assertThat(config.getValue(), equalTo(Arrays.asList("one", "two, three")));
    }

    @Test
    public void initDefaultTest() {
        OverwriteParamsConfig config = new OverwriteParamsConfig();
        config.setValue(Arrays.asList("one", "two, three"));
        config.initDefault();

        assertThat(config.getValue(), equalTo(Collections.emptyList()));
    }

    @Test
    public void applyConfigTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url(MockServer.GET_PATH)
                .method(RequestMethod.GET)
                .config(overwriteParams("one"))
                .param("one", "one value1")
                .param("two", "two value1")
                .param("one", "one value2")
                .param("two", "two value2")
                .param("three", "three value");

        RequestContext context = new RequestContext(spec);
        processRequest(context);

        MultiKeyMap<String, Object> params = context.getRequestSpec().getParams();

        assertThat(params.size(), equalTo(4));

        assertThat(params.size(), equalTo(4));
        assertThat(params.getFirst("one"), equalTo("one value2"));
        assertThat(params.getFirst("two"), equalTo("two value1"));
        assertThat(params.getLast("two"), equalTo("two value2"));
        assertThat(params.getFirst("three"), equalTo("three value"));
    }
}
