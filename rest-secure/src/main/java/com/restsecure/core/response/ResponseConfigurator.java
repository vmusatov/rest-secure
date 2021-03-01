package com.restsecure.core.response;

import com.restsecure.RestSecure;
import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.http.Header;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.Validation;
import org.apache.http.HttpEntity;
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

        precessResponse(response);
        validateResponse(response);

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

            String bodyContent = getBodyContent(httpResponse);

            context.getConfigValue(ObjectMapperConfig.class)
                    .ifPresentOrElse(
                            objectMapper -> response.setBody(new ResponseBody(bodyContent, objectMapper)),
                            () -> response.setBody(new ResponseBody(bodyContent))
                    );

            response.setContext(context);

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
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return "";
            }
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RestSecureException(e.getMessage());
        }
    }

    private static void validateResponse(Response response) {
        RequestContext context = response.getContext();
        List<Validation> validations = context.getSpecification().getValidations();

        if (validations == null) {
            return;
        }

        validations.forEach(validation -> validation.validate(response));
    }

    private static void precessResponse(Response response) {
        RequestContext context = response.getContext();
        List<Processor> processors = new ArrayList<>();

        processors.addAll(RestSecure.getContext().getProcessors());
        processors.addAll(context.getSpecification().getProcessors());

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getResponseProcessOrder))
                .forEach(processor -> processor.processResponse(response));
    }
}
