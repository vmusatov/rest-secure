package com.restsecure;

import com.restsecure.mapper.DefaultJacksonResponseMapper;
import com.restsecure.mapper.ResponseMapper;
import com.restsecure.request.handler.GeneralRequestHandler;
import com.restsecure.request.handler.RequestConfigurationHandler;
import com.restsecure.request.handler.RequestDataHandler;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.RequestSpecificationImpl;
import lombok.Getter;
import lombok.Setter;

public class RestSecureConfiguration {

    public static final String DEFAULT_SESSION_ID = "JSESSIONID";
    public static final String DEFAULT_URL = "http://localhost";

    @Getter
    @Setter
    private static String sessionId = DEFAULT_SESSION_ID;

    @Getter
    @Setter
    private static String baseUrl = "";

    @Getter
    @Setter
    private static ResponseMapper mapper = new DefaultJacksonResponseMapper();

    @Getter
    static RequestSpecification defaultRequestSpecification = new RequestSpecificationImpl();

    @Getter
    private static GeneralRequestHandler generalRequestHandler = new GeneralRequestHandler()
            .addHandler(new RequestConfigurationHandler())
            .addHandler(new RequestDataHandler());

    public static void reset() {
        sessionId = DEFAULT_SESSION_ID;
        baseUrl = "";
        mapper = new DefaultJacksonResponseMapper();
        defaultRequestSpecification = new RequestSpecificationImpl();
        generalRequestHandler = new GeneralRequestHandler()
                .addHandler(new RequestConfigurationHandler())
                .addHandler(new RequestDataHandler());
    }
}
