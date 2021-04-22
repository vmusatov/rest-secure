package com.restsecure.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Host {
    @Getter
    private String name;
    @Getter
    private int port;
}
