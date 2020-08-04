package com.restsecure.core.mapping.serialize;

public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(Throwable throwable) {
        super(throwable);
    }
}