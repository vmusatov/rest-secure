package com.restsecure.authentication;

import com.restsecure.core.http.HeaderNames;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;

import java.util.Base64;

public class BasicAuthentication implements Processor {
    private final String userName;
    private final String password;

    public BasicAuthentication(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void processRequest(RequestContext context) {
        String nameAndPass = userName + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(nameAndPass.getBytes());

        context.getSpecification().header(HeaderNames.AUTHORIZATION, "Basic " + encoded);
    }
}
