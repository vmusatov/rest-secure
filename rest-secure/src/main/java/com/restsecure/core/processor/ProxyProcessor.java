package com.restsecure.core.processor;

import com.restsecure.core.configuration.configs.HttpClientBuilderConfig;
import com.restsecure.core.configuration.configs.HttpClientContextConfig;
import com.restsecure.core.http.Proxy;
import com.restsecure.core.request.RequestContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

@ProcessAll
public class ProxyProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        Proxy proxy = context.getSpecification().getProxy();

        if (proxy == null) {
            return;
        }

        HttpHost host = new HttpHost(proxy.getHost(), proxy.getPort());
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(host);

        HttpClientBuilder httpClientBuilder = context.getConfigValue(HttpClientBuilderConfig.class);

        if (proxy.needAuth()) {
            HttpClientContext httpClientContext = context.getConfigValue(HttpClientContextConfig.class);

            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword()));

            AuthCache authCache = new BasicAuthCache();
            BasicScheme basicAuth = new BasicScheme();

            authCache.put(host, basicAuth);

            httpClientContext.setCredentialsProvider(credentialsProvider);
            httpClientContext.setAuthCache(authCache);
        }

        httpClientBuilder.setRoutePlanner(routePlanner);
    }
}
