package com.restsecure.core.http.proxy;

import com.restsecure.MockServer;
import com.restsecure.ProxyServer;
import com.restsecure.RestSecure;
import com.restsecure.core.client.apache.ApacheHttpClient;
import com.restsecure.core.http.Host;
import org.testng.annotations.*;

import static com.restsecure.Configs.httpClient;
import static com.restsecure.Configs.proxy;
import static com.restsecure.Validations.body;
import static com.restsecure.Validations.statusCode;
import static com.restsecure.core.http.StatusCode.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProxyTest {

    private Host proxyHost = new Host("localhost", 7777);

    private ApacheHttpClient clientWithProxyCreds = ApacheHttpClient.custom()
            .addCredentials(proxyHost, "u", "p")
            .build();

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

    @AfterClass
    public void closeClient() {
        clientWithProxyCreds.close();
    }

    @Test
    public void noAuthProxyTest() {
        RestSecure.get(MockServer.GET_PATH)
                .config(proxy(proxyHost))
                .expect(
                        statusCode(OK),
                        body("proxied body")
                )
                .send();

        assertThat(ProxyServer.isUsed(), equalTo(true));
    }

    @Test
    public void authProxyTest() {

        RestSecure.get(MockServer.GET_PATH)
                .config(
                        httpClient(clientWithProxyCreds),
                        proxy(proxyHost)
                )
                .expect(
                        statusCode(OK),
                        body("proxied body")
                )
                .send();

        assertThat(ProxyServer.isUsed(), equalTo(true));
    }
}
