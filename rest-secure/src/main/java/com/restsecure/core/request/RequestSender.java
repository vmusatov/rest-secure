package com.restsecure.core.request;

import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for sending multiple requests
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
            responses.add(spec.send());
        }
        return responses;
    }
}
