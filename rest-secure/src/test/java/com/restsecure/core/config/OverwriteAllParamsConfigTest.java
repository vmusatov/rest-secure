package com.restsecure.core.config;

import com.restsecure.BaseTest;
import com.restsecure.MockServer;
import com.restsecure.core.configuration.configs.OverwriteAllParamsConfig;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecImpl;
import com.restsecure.core.util.MultiKeyMap;
import org.testng.annotations.Test;

import static com.restsecure.Configs.overwriteAllParams;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OverwriteAllParamsConfigTest extends BaseTest {

    @Test
    public void setValueTest() {
        OverwriteAllParamsConfig config = new OverwriteAllParamsConfig();
        config.setValue(true);

        assertThat(config.getValue(), equalTo(true));
    }

    @Test
    public void getValueTest() {
        OverwriteAllParamsConfig config = new OverwriteAllParamsConfig();
        assertThat(config.getValue(), equalTo(null));

        config.setValue(true);
        assertThat(config.getValue(), equalTo(true));
    }

    @Test
    public void initDefaultTest() {
        OverwriteAllParamsConfig config = new OverwriteAllParamsConfig();
        config.setValue(true);
        config.initDefault();

        assertThat(config.getValue(), equalTo(false));
    }

    @Test
    public void applyConfigTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url(MockServer.GET_PATH)
                .method(RequestMethod.GET)
                .config(overwriteAllParams(true))
                .param("one", "one value1")
                .param("two", "two value1")
                .param("one", "one value2")
                .param("two", "one value2")
                .param("three", "three value")
                .param("two", "two value3");

        RequestContext context = new RequestContext(spec);
        processRequest(context);

        MultiKeyMap<String, Object> params = context.getRequestSpec().getParams();

        assertThat(params.size(), equalTo(3));
        assertThat(params.getFirst("one"), equalTo("one value2"));
        assertThat(params.getFirst("two"), equalTo("two value3"));
        assertThat(params.getFirst("three"), equalTo("three value"));
    }
}
