package com.restsecure.response;

import com.restsecure.components.configuration.ConfigFactory;
import com.restsecure.components.deserialize.DeserializeConfig;
import com.restsecure.components.logging.handler.ResponseLogHandler;
import com.restsecure.components.logging.logger.ResponseLogger;
import com.restsecure.http.Header;
import com.restsecure.http.HttpHelper;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.handler.ResponseHandler;
import com.restsecure.response.validation.ResponseValidation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseConfigurator {

    public static Response configureResponse(CloseableHttpResponse httpResponse, RequestSpecification spec) {
        Response response = parseHttpResponse(httpResponse, spec);

        validateResponse(response, spec.getValidations());
        handleResponse(response, spec);

        return response;
    }

    private static Response parseHttpResponse(CloseableHttpResponse httpResponse, RequestSpecification spec) {
        try (httpResponse) {
            Response response = new HttpResponse();
            List<Header> headers = parseHeaders(httpResponse);

            response.setHeaders(headers);
            response.setCookies(HttpHelper.getCookiesFromHeaders(headers));
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());

            DeserializeConfig deserializeConfig = ConfigFactory.getConfigOrCreateDefault(spec.getConfigs(), DeserializeConfig.class);
            response.setBody(new ResponseBody(getBodyContent(httpResponse), deserializeConfig));

            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Header> parseHeaders(CloseableHttpResponse httpResponse) {
        List<org.apache.http.Header> apacheHeaders = Arrays.asList(httpResponse.getAllHeaders());
        return apacheHeaders.stream()
                .map(apacheHeader -> new Header(apacheHeader.getName(), apacheHeader.getValue()))
                .collect(Collectors.toList());
    }

    private static String getBodyContent(org.apache.http.HttpResponse response) {
        try {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void validateResponse(Response response, List<ResponseValidation> validations) {
        for (ResponseValidation validation : validations) {
            validation.validate(response);
        }
    }

    private static void handleResponse(Response response, RequestSpecification spec) {
        new ResponseLogHandler().handleResponse(response, spec);
        for (ResponseHandler handler : spec.getResponseHandlers()) {
            handler.handleResponse(response, spec);
        }
    }
}
