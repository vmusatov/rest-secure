package com.restsecure.core.request;

import com.restsecure.core.apache.ApacheConfig;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.exception.SendRequestException;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.ResponseConfigurator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for sending multiple requests and requests in one session
 */
public class RequestSender {

    /**
     * Allows you to send multiple requests at once
     *
     * @param processor       Processor
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return last request response
     */
    public static Response send(Processor processor, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        List<RequestSpecification> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(processor, requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processor Processor
     * @param specs     RequestSpecification list
     * @return last request response
     */
    public static Response send(Processor processor, List<RequestSpecification> specs) {
        for (RequestSpecification spec : specs) {
            spec.process(processor);
        }
        return send(specs);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecification list
     * @return last request response
     */
    public static Response send(RequestSpecification spec, RequestSpecification... additionalSpecs) {
        List<RequestSpecification> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param specs RequestSpecifications list
     * @return last request response
     */
    public static Response send(List<RequestSpecification> specs) {
        for (int i = 0; i < specs.size() - 1; i++) {
            send(specs.get(i));
        }
        return send(specs.get(specs.size() - 1));
    }

    /**
     * Allows you to send request by specified RequestSpecification
     *
     * @param spec RequestSpecification
     * @return Response
     */
    public static Response send(RequestSpecification spec) {

        RequestContext context = new RequestContext(spec);
        HttpUriRequest request = RequestFactory.createRequest(context);

        ApacheConfig apacheConfig = context.getConfig(ApacheConfig.class);
        HttpClientBuilder httpClientBuilder = apacheConfig.getHttpClientBuilder();
        HttpClientContext httpClientContext = apacheConfig.getHttpClientContext();

        try (CloseableHttpClient httpClient = httpClientBuilder.build(); httpClient) {
            context.setRequestTime(System.currentTimeMillis());
            CloseableHttpResponse httpResponse = httpClient.execute(request, httpClientContext);

            return ResponseConfigurator.configureResponse(httpResponse, context);
        } catch (IOException e) {
            throw new SendRequestException(e);
        }
    }
}
