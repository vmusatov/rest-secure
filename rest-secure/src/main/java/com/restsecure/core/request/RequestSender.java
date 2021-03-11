package com.restsecure.core.request;

import com.restsecure.core.configuration.configs.HttpClientBuilderConfig;
import com.restsecure.core.configuration.configs.HttpClientContextConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpec;
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
     * @param spec            RequestSpec
     * @param additionalSpecs RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(Processor processor, RequestSpec spec, RequestSpec... additionalSpecs) {
        List<RequestSpec> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(processor, requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processors      Processor list
     * @param spec            RequestSpec
     * @param additionalSpecs RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(List<Processor> processors, RequestSpec spec, RequestSpec... additionalSpecs) {
        List<RequestSpec> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(processors, requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processor Processor
     * @param specs     RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(Processor processor, List<RequestSpec> specs) {
        for (RequestSpec spec : specs) {
            spec.process(processor);
        }
        return send(specs);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param processors Processors list
     * @param specs      RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(List<Processor> processors, List<RequestSpec> specs) {
        for (RequestSpec spec : specs) {
            spec.process(processors);
        }
        return send(specs);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param spec            RequestSpec
     * @param additionalSpecs RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(RequestSpec spec, RequestSpec... additionalSpecs) {
        List<RequestSpec> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(requests);
    }

    /**
     * Allows you to send multiple requests at once
     *
     * @param specs RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(List<RequestSpec> specs) {
        List<Response> responses = new ArrayList<>();
        for (RequestSpec spec : specs) {
            responses.add(send(spec));
        }
        return responses;
    }

    /**
     * Allows you to send request by specified RequestSpec
     *
     * @param spec RequestSpec
     * @return Response
     */
    public static Response send(RequestSpec spec) {

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
