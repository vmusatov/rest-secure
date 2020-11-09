package com.restsecure.core.response;

import com.restsecure.RestSecure;
import com.restsecure.core.configuration.configs.DeserializerConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.mapping.deserialize.Deserializer;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseConfigurator {

    public static Response configureResponse(CloseableHttpResponse httpResponse, RequestContext context) {
        Response response = parseHttpResponse(httpResponse, context);

        validateResponse(context, response);
        precessResponse(context, response);

        return response;
    }

    private static Response parseHttpResponse(CloseableHttpResponse httpResponse, RequestContext context) {
        try (httpResponse) {
            Response response = new HttpResponse();
            response.setTime(System.currentTimeMillis() - context.getRequestTime());

            List<Header> headers = parseHeaders(httpResponse);
            response.setHeaders(headers);
            response.setCookies(HttpHelper.getCookiesFromHeaders(headers));

            response.setStatusLine(httpResponse.getStatusLine().toString());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());

            Deserializer deserializer = context.getConfigValue(DeserializerConfig.class);
            response.setBody(new ResponseBody(getBodyContent(httpResponse), deserializer));

            return response;
        } catch (IOException e) {
            throw new RestSecureException(e);
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
            throw new RestSecureException(e.getMessage());
        }
    }

    private static void validateResponse(RequestContext context, Response response) {
        for (Validation validation : context.getSpecification().getValidations()) {
            ValidationResult result = validation.validate(context, response);

            if (result.isFail()) {
                throw new AssertionError(result.getErrorText());
            }
        }
    }

    private static void precessResponse(RequestContext context, Response response) {
        List<Processor> processors = new ArrayList<>();
        processors.addAll(RestSecure.getContext().getProcessors());
        processors.addAll(context.getSpecification().getProcessors());

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getResponseProcessOrder))
                .forEach(processor -> processor.processResponse(context, response));
    }
}
