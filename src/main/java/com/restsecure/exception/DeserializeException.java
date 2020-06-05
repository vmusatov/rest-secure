package com.restsecure.exception;

public class DeserializeException extends RuntimeException {
    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(Throwable throwable) {
        super(throwable);
    }
}
