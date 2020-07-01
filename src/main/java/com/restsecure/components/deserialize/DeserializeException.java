package com.restsecure.components.deserialize;

public class DeserializeException extends RuntimeException {
    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(Throwable throwable) {
        super(throwable);
    }
}
