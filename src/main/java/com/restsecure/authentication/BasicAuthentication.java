package com.restsecure.authentication;

import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.request.RequestContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class BasicAuthentication implements PreSendProcessor {
    private final String userName;
    private final String password;

    public BasicAuthentication(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void preSendProcess(RequestContext context) {
        int port = context.getSpecification().getPort();
        String url = context.getSpecification().getUrl();
        String hostName = HttpHelper.getDomainName(url);

        HttpHost targetHost = new HttpHost(hostName, port);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        context.getHttpClientContext().setCredentialsProvider(credsProvider);
        context.getHttpClientContext().setAuthCache(authCache);
    }
}
