package com.restsecure.request.authentication;

import com.restsecure.http.HttpHelper;
import com.restsecure.request.specification.RequestSpecification;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class BasicAuthentication extends RequestAuthHandler {
    private final String userName;
    private final String password;

    public BasicAuthentication(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void authenticate(RequestSpecification spec) {
        int port = spec.getPort();
        String url = spec.getUrl();
        String hostName = HttpHelper.getDomainName(url);

        HttpHost targetHost = new HttpHost(hostName, port);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        spec.getContext().setCredentialsProvider(credsProvider);
        spec.getContext().setAuthCache(authCache);
    }
}
