package com.restsecure.core.http.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Proxy {
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public Proxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.username = null;
        this.password = null;
    }

    public boolean needAuth() {
        return this.username != null && this.password != null;
    }
}
