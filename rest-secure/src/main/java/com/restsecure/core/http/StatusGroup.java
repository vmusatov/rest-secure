package com.restsecure.core.http;

import lombok.Getter;

public enum StatusGroup {
    INFORMATIONAL(100, 199),
    SUCCESSFUL(200, 299),
    REDIRECTION(300, 399),
    CLIENT_ERROR(400, 499),
    SERVER_ERROR(500, 599);

    @Getter
    private int from;
    @Getter
    private int to;

    StatusGroup(int from, int to) {
        this.from = from;
        this.to = to;
    }
}
