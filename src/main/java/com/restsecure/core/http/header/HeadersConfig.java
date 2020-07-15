package com.restsecure.core.http.header;

import com.restsecure.core.configuration.Config;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeadersConfig implements Config {

    @Getter
    private List<String> overrideHeaders;

    public HeadersConfig() {
        this.overrideHeaders = new ArrayList<>();
    }

    public HeadersConfig overrideHeaders(String headerName, String... additionalHeadersNames) {
        this.overrideHeaders.add(headerName);
        this.overrideHeaders.addAll(Arrays.asList(additionalHeadersNames));
        return this;
    }

    public HeadersConfig overrideHeaders(List<String> headerNames) {
        this.overrideHeaders.addAll(headerNames);
        return this;
    }

    @Override
    public void reset() {
        this.overrideHeaders = new ArrayList<>();
    }
}
