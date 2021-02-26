package com.restsecure.core.config;

import com.restsecure.MockServer;
import com.restsecure.RestSecure;
import com.restsecure.core.configuration.configs.OverwriteParamsConfig;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.RequestFactory;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.util.MultiKeyMap;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.restsecure.Configs.overwriteParams;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OverwriteParamsConfigTest {

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
        RequestSpecification spec = RestSecure.get(MockServer.GET_PATH)
                .config(overwriteParams("one"))
                .param("one", "one value1")
                .param("two", "two value1")
                .param("one", "one value2")
                .param("two", "two value2")
                .param("three", "three value");

        RequestContext context = new RequestContext(spec);
        RequestFactory.createRequest(context);

        MultiKeyMap<String, Object> params = context.getSpecification().getParameters();

        assertThat(params.size(), equalTo(4));

        assertThat(params.size(), equalTo(4));
        assertThat(params.getFirst("one"), equalTo("one value2"));
        assertThat(params.getFirst("two"), equalTo("two value1"));
        assertThat(params.getLast("two"), equalTo("two value2"));
        assertThat(params.getFirst("three"), equalTo("three value"));
    }
}