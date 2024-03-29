package com.restsecure.core.client.apache;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApacheResponseTransformer implements ResponseTransformer<CloseableHttpResponse> {

    @Override
    public MutableResponse transform(CloseableHttpResponse apacheResponse, RequestContext context) {
        try (apacheResponse) {
            MutableResponse response = new HttpResponse();
            response.setTime(System.currentTimeMillis() - context.getRequestTime());

            List<Header> headers = parseHeaders(apacheResponse);
            response.setHeaders(headers);
            response.setCookies(HttpHelper.getCookiesFromHeaders(headers));

            response.setStatusLine(String.valueOf(apacheResponse.getStatusLine()));
            response.setStatusCode(apacheResponse.getStatusLine().getStatusCode());

            byte[] bodyContent = getBodyContent(apacheResponse);

            context.getConfigValue(ObjectMapperConfig.class)
                    .ifPresentOrElse(
                            objectMapper -> response.setBody(new ResponseBody(bodyContent, objectMapper)),
                            () -> response.setBody(new ResponseBody(bodyContent))
                    );

            return response;
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }

    private static List<Header> parseHeaders(org.apache.http.HttpResponse httpResponse) {
        List<org.apache.http.Header> apacheHeaders = Arrays.asList(httpResponse.getAllHeaders());
        return apacheHeaders.stream()
                .map(apacheHeader -> new Header(apacheHeader.getName(), apacheHeader.getValue()))
                .collect(Collectors.toList());
    }

    private static byte[] getBodyContent(org.apache.http.HttpResponse response) {
        try {
            HttpEntity entity = response.getEntity();
            if (entity == null || entity.getContent() == null) {
                return null;
            }
            return entity.getContent().readAllBytes();
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }
}
