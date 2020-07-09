package com.restsecure.core.deserialize;

public class DeserializeException extends RuntimeException {
    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(Throwable throwable) {
        super(throwable);
    }
}
