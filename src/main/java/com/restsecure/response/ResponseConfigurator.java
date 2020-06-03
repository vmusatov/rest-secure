package com.restsecure.response;

import com.restsecure.http.HttpHelper;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.validation.ResponseValidation;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseConfigurator {

    public static Response configureResponse(CloseableHttpResponse httpResponse, RequestSpecification spec) {
        Response response = parseHttpResponse(httpResponse);

        validateResponse(response, spec.getValidations());
        handleResponse(response, spec.getResponseHandlers());

        return response;
    }

    private static Response parseHttpResponse(CloseableHttpResponse httpResponse) {
        try (httpResponse) {
            Response response = new HttpResponse();

            List<Header> apacheHeaders = Arrays.asList(httpResponse.getAllHeaders());
            List<com.restsecure.http.Header> headers = apacheHeaders.stream()
                    .map(apacheHeader -> new com.restsecure.http.Header(apacheHeader.getName(), apacheHeader.getValue()))
                    .collect(Collectors.toList());

            response.setHeaders(headers);
            response.setCookies(HttpHelper.getCookiesFromHeaders(headers));
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            response.setBody(new ResponseBody(getBodyContent(httpResponse)));

            return response;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
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

    private static void handleResponse(Response response, List<ResponseHandler> handlers) {
        for (ResponseHandler handler : handlers) {
            handler.handleResponse(response);
        }
    }
}
