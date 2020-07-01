package com.restsecure.request;

import com.restsecure.RestSecure;
import com.restsecure.components.data.RequestDataHandler;
import com.restsecure.components.logging.handler.RequestLogHandler;
import com.restsecure.request.exception.RequestConfigurationException;
import com.restsecure.request.handler.RequestConfigurationHandler;
import com.restsecure.request.handler.RequestHandler;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.SpecificationValidator;
import org.apache.http.client.methods.*;

import java.net.URI;

import static com.restsecure.http.HttpHelper.*;

public class RequestFactory {

    public static HttpUriRequest createRequest(RequestSpecification specification) {

        handleSpecification(specification);
        SpecificationValidator.validate(specification);

        switch (specification.getMethod()) {
            case GET:
                return createGet(specification);
            case DELETE:
                return createDelete(specification);
            case PUT:
                return createPut(specification);
            case POST:
                return createPost(specification);
            default:
                throw new RequestConfigurationException("Unsupported request method " + specification.getMethod());
        }
    }

    private static void handleSpecification(RequestSpecification specification) {
        new RequestDataHandler().handleRequest(specification);
        new RequestConfigurationHandler().handleRequest(specification);

        specification.handleRequest(RestSecure.getGlobalRequestHandlers());

        for (RequestHandler handler : specification.getRequestHandlers()) {
            handler.handleRequest(specification);
        }

        new RequestLogHandler().handleRequest(specification);
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
