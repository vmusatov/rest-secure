package com.restsecure.core.response;

import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.NameValueList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpResponse implements Response {
    @Getter
    @Setter
    private ResponseBody body;
    @Getter
    @Setter
    private int statusCode;
    @Getter
    @Setter
    private String statusLine;
    @Getter
    private NameValueList<Header> headers;
    @Getter
    private NameValueList<Cookie> cookies;
    @Getter
    @Setter
    private long time;
    @Getter
    @Setter
    private RequestContext context;

    public HttpResponse() {
        this.headers = new NameValueList<>();
        this.cookies = new NameValueList<>();
        this.context = new RequestContext();
        this.body = new ResponseBody("");
        this.statusLine = "";
    }

    @Override
    public void setHeaders(List<Header> headers) {
        this.headers = new NameValueList<>(headers);
    }

    @Override
    public void setCookies(List<Cookie> cookies) {
        this.cookies = new NameValueList<>(cookies);
    }

    @Override
    public long getTimeIn(TimeUnit timeUnit) {
        return timeUnit.convert(this.time, TimeUnit.MILLISECONDS);
    }
}
