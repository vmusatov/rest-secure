package com.restsecure.core.http.proxy;

import com.restsecure.MockServer;
import com.restsecure.ProxyServer;
import com.restsecure.RestSecure;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.restsecure.Validations.body;
import static com.restsecure.Validations.statusCode;
import static com.restsecure.core.http.StatusCode.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProxyTest {

    @BeforeClass
    public void resetSpec() {
        RestSecure.resetGlobalSpec();
    }

    @BeforeMethod
    public void run() {
        MockServer.reset();
        MockServer.setResponseBody("proxied body");
        ProxyServer.runServer("localhost", MockServer.PORT, 7777);
    }

    @AfterMethod
    public void teardown() {
        MockServer.reset();
        ProxyServer.teardown();
    }

    @Test
    public void noAuthProxyTest() {
        RestSecure.get(MockServer.GET_PATH)
                .proxy("localhost", 7777)
                .expect(
                        statusCode(OK),
                        body(equalTo("proxied body"))
                )
                .send();

        assertThat(ProxyServer.isUsed(), equalTo(true));
    }

    @Test
    public void authProxyTest() {
        RestSecure.get(MockServer.GET_PATH)
                .proxy("localhost", 7777, "username", "pass")
                .expect(
                        statusCode(OK),
                        body(equalTo("proxied body"))
                )
                .send();

        assertThat(ProxyServer.isUsed(), equalTo(true));
    }
}
