package com.restsecure.core.exception;

public class SendRequestException extends RuntimeException {
    public SendRequestException(String message) {
        super(message);
    }

    public SendRequestException(Throwable throwable) {
        super(throwable);
    }
}
