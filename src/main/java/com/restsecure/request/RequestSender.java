package com.restsecure.request;

import com.restsecure.RestSecure;
import com.restsecure.request.exception.SendRequestException;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.RequestSpecificationImpl;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseConfigurator;
import com.restsecure.components.session.Session;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for sending multiple requests and requests in one session
 */
public class RequestSender {

    /**
     * Allows you to send multiple requests in one session at once
     *
     * @param session         request {@link Session}
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return last request response
     */
    public static Response send(Session session, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        List<RequestSpecification> requests = new ArrayList<>();
        requests.add(spec);
        requests.addAll(Arrays.asList(additionalSpecs));

        return send(session, requests);
    }

    /**
     * Allows you to send multiple requests in one session at once
     *
     * @param session request {@link Session}
     * @param specs   RequestSpecification list
     * @return last request response
     */
    public static Response send(Session session, List<RequestSpecification> specs) {
        for (RequestSpecification spec : specs) {
            spec.session(session);
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
     * Allows you to send request by specified specification
     *
     * @param spec RequestSpecification
     * @return Response
     */
    public static Response send(RequestSpecification spec) {

        RequestSpecification specification = new RequestSpecificationImpl()
                .mergeWith(RestSecure.globalSpecification)
                .mergeWith(spec);

        HttpUriRequest request = RequestFactory.createRequest(specification);

        try (CloseableHttpClient httpClient = specification.getBuilder().build(); httpClient) {
            CloseableHttpResponse httpResponse = httpClient.execute(request, specification.getContext());

            return ResponseConfigurator.configureResponse(httpResponse, specification);
        } catch (IOException e) {
            throw new SendRequestException(e);
        }
    }
}
