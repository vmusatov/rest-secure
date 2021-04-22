package com.restsecure.core.client.apache;

import com.restsecure.core.client.AbstractHttpClient;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.Host;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.ResponseTransformer;
import lombok.experimental.Accessors;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApacheHttpClient extends AbstractHttpClient {

    private CloseableHttpClient client;
    private ResponseTransformer<HttpResponse> responseTransformer;

    private ApacheHttpClient(CloseableHttpClient client) {
        this.client = client;
        this.responseTransformer = new ApacheResponseTransformer();
    }

    public static ApacheHttpClient create() {
        return new Builder().build();
    }

    public static Builder custom() {
        return new Builder();
    }

    @Override
    public Response doRequest(RequestContext context) {
        HttpUriRequest request = ApacheRequestFactory.createRequest(context);

        try {
            CloseableHttpResponse apacheResponse = client.execute(request);
            Response response = responseTransformer.transform(apacheResponse, context);
            apacheResponse.close();

            return response;
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }

    @Override
    public void close() {
        try {
            this.client.close();
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }

    @Accessors(chain = true)
    public static class Builder {

        private HttpClientBuilder apacheBuilder;
        private Map<HttpHost, Credentials> credentials;

        public Builder() {
            this.apacheBuilder = HttpClientBuilder.create();
            this.credentials = new HashMap<>();
        }

        public Builder addCredentials(String host, int port, String username, String password) {
            this.credentials.put(
                    new HttpHost(host, port),
                    new UsernamePasswordCredentials(username, password)
            );
            return this;
        }

        public Builder addCredentials(Host host, String username, String password) {
            this.credentials.put(
                    new HttpHost(host.getName(), host.getPort()),
                    new UsernamePasswordCredentials(username, password)
            );
            return this;
        }

        public ApacheHttpClient build() {
            apacheBuilder
                    .setDefaultCredentialsProvider(createCredentialsProvider())
                    .useSystemProperties();

            return new ApacheHttpClient(apacheBuilder.build());
        }

        private CredentialsProvider createCredentialsProvider() {
            CredentialsProvider provider = new BasicCredentialsProvider();

            for (Map.Entry<HttpHost, Credentials> creds : credentials.entrySet()) {
                if (creds == null || creds.getKey() == null || creds.getValue() == null) {
                    continue;
                }
                AuthScope authscope = new AuthScope(creds.getKey());
                provider.setCredentials(authscope, creds.getValue());
            }

            return provider;
        }
    }
}
