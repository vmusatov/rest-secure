package com.restsecure.core.request;

import com.restsecure.core.configuration.configs.HttpClientBuilderConfig;
import com.restsecure.core.configuration.configs.HttpClientContextConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.processor.Processor;
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
     * @return responses list
     */
    public static List<Response> send(Processor processor, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        List<RequestSpecification> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(processor, requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processors      Processor list
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return responses list
     */
    public static List<Response> send(List<Processor> processors, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        List<RequestSpecification> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(processors, requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processor Processor
     * @param specs     RequestSpecification list
     * @return responses list
     */
    public static List<Response> send(Processor processor, List<RequestSpecification> specs) {
        for (RequestSpecification spec : specs) {
            spec.process(processor);
        }
        return send(specs);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processors Processors list
     * @param specs      RequestSpecification list
     * @return responses list
     */
    public static List<Response> send(List<Processor> processors, List<RequestSpecification> specs) {
        for (RequestSpecification spec : specs) {
            spec.process(processors);
        }
        return send(specs);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecification list
     * @return responses list
     */
    public static List<Response> send(RequestSpecification spec, RequestSpecification... additionalSpecs) {
        List<RequestSpecification> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param specs RequestSpecifications list
     * @return responses list
     */
    public static List<Response> send(List<RequestSpecification> specs) {
        List<Response> responses = new ArrayList<>();
        for (RequestSpecification spec : specs) {
            responses.add(send(spec));
        }
        return responses;
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

        HttpClientBuilder httpClientBuilder = context.getConfigValue(HttpClientBuilderConfig.class);
        HttpClientContext httpClientContext = context.getConfigValue(HttpClientContextConfig.class);

        try (CloseableHttpClient httpClient = httpClientBuilder.build(); httpClient) {
            context.setRequestTime(System.currentTimeMillis());
            CloseableHttpResponse apacheResponse = httpClient.execute(request, httpClientContext);

            Response response = ResponseConfigurator.configureResponse(apacheResponse, context);
            apacheResponse.close();

            return response;
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }
}
