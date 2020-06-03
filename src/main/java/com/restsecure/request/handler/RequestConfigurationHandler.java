package com.restsecure.request.handler;

import com.restsecure.RestSecureConfiguration;
import com.restsecure.request.RequestHandler;
import com.restsecure.request.specification.RequestSpecification;

/**
 * This request handler extracts data from the configuration and adds it to the request specification.
 */
public class RequestConfigurationHandler implements RequestHandler {
    @Override
    public void handleRequest(RequestSpecification spec) {
        String baseUrl = RestSecureConfiguration.getBaseUrl();
        String requestUrl = spec.getUrl();

        String resultUrl = baseUrl + requestUrl;

        if (resultUrl.isEmpty()) {
            resultUrl = RestSecureConfiguration.DEFAULT_URL;
        }

        spec.url(resultUrl);
    }
}
