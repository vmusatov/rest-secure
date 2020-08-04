package com.restsecure.core.exception;

public class RestSecureException extends RuntimeException {
    public RestSecureException(String message) {
        super(message);
    }

    public RestSecureException(Throwable cause) {
        super(cause);
    }
}
