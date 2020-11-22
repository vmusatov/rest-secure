package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.SpecificationValidator;
import org.apache.http.client.methods.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.restsecure.core.http.HttpHelper.*;

public class RequestFactory {

    public static HttpUriRequest createRequest(RequestContext context) {

        processRequest(context);
        SpecificationValidator.validate(context.getSpecification());

        switch (context.getSpecification().getMethod()) {
            case GET:
                return createGet(context.getSpecification());
            case DELETE:
                return createDelete(context.getSpecification());
            case PUT:
                return createPut(context);
            case POST:
                return createPost(context);
            default:
                throw new RequestConfigurationException("Unsupported request method " + context.getSpecification().getMethod());
        }
    }

    private static void processRequest(RequestContext context) {
        List<Processor> processors = new ArrayList<>();
        processors.addAll(RestSecure.getContext().getProcessors());
        processors.addAll(context.getSpecification().getProcessors());

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getRequestProcessOrder))
                .forEach(processor -> processor.processRequest(context));
    }

    private static HttpUriRequest createPost(RequestContext context) {
        RequestSpecification spec = context.getSpecification();

        URI uri = buildUri(spec);
        HttpPost request = new HttpPost(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static HttpUriRequest createGet(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpGet request = new HttpGet(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPut(RequestContext context) {
        RequestSpecification spec = context.getSpecification();

        URI uri = buildUri(spec);
        HttpPut request = new HttpPut(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static HttpUriRequest createDelete(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpDelete request = new HttpDelete(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }
}
