package com.restsecure.request.exception;

public class SendRequestException extends RuntimeException {
    public SendRequestException(String message) {
        super(message);
    }

    public SendRequestException(Throwable throwable) {
        super(throwable);
    }
}
