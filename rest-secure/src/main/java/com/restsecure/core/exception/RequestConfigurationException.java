package com.restsecure.core.exception;

public class RequestConfigurationException extends RuntimeException {
    public RequestConfigurationException(String message) {
        super(message);
    }

    public RequestConfigurationException(Throwable throwable) {
        super(throwable);
    }
}
