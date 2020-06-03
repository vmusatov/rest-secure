package com.restsecure.request;

import com.restsecure.RestSecureConfiguration;
import com.restsecure.exception.RequestConfigurationException;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.RequestSpecificationImpl;
import com.restsecure.request.specification.SpecificationValidator;
import org.apache.http.client.methods.*;

import java.net.URI;

import static com.restsecure.http.HttpHelper.*;

public class RequestFactory {

    public static HttpUriRequest createRequest(RequestSpecification specification) {

        RequestSpecification spec = new RequestSpecificationImpl()
                .mergeWith(RestSecureConfiguration.getDefaultRequestSpecification())
                .mergeWith(specification);

        SpecificationValidator.validate(spec);
        handleRequest(spec);

        switch (spec.getMethod()) {
            case GET:
                return createGet(spec);
            case DELETE:
                return createDelete(spec);
            case PUT:
                return createPut(spec);
            case POST:
                return createPost(spec);
            default:
                throw new RequestConfigurationException("Unsupported request method " + spec.getMethod());
        }
    }

    private static void handleRequest(RequestSpecification specification) {
        RestSecureConfiguration.getGeneralRequestHandler().handleRequest(specification);

        for (RequestHandler handler : specification.getRequestHandlers()) {
            handler.handleRequest(specification);
        }
    }

    private static HttpUriRequest createPost(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpPost request = new HttpPost(uri);
        setHeadersToRequest(specification.getHeaders(), request);
        setEntityToRequest(specification, request);

        return request;
    }

    private static HttpUriRequest createGet(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpGet request = new HttpGet(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPut(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpPut request = new HttpPut(uri);
        setHeadersToRequest(specification.getHeaders(), request);
        setEntityToRequest(specification, request);

        return request;
    }

    private static HttpUriRequest createDelete(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpDelete request = new HttpDelete(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }
}
