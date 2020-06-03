package com.restsecure.response;

import com.restsecure.http.Cookie;
import com.restsecure.http.Header;
import com.restsecure.http.MultiValueList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class HttpResponse implements Response {
    @Getter
    @Setter
    private ResponseBody body;
    @Getter
    @Setter
    private int statusCode;
    private MultiValueList<Header> headers;
    private MultiValueList<Cookie> cookies;

    public HttpResponse() {
        this.headers = new MultiValueList<>();
        this.cookies = new MultiValueList<>();
    }

    @Override
    public MultiValueList<Header> getHeaders() {
        return headers;
    }

    @Override
    public void setHeaders(List<Header> headers) {
        this.headers = new MultiValueList<>(headers);
    }

    @Override
    public MultiValueList<Cookie> getCookies() {
        return this.cookies;
    }

    @Override
    public void setCookies(List<Cookie> cookies) {
        this.cookies = new MultiValueList<>(cookies);
    }
}
