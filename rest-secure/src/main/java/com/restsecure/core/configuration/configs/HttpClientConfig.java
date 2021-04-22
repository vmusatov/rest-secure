package com.restsecure.core.configuration.configs;

import com.restsecure.core.client.apache.ApacheHttpClient;
import com.restsecure.core.client.Client;
import com.restsecure.core.configuration.BaseConfig;

public class HttpClientConfig extends BaseConfig<Client> {

    @Override
    public void initDefault() {
        value = ApacheHttpClient.create();
    }
}
