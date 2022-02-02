package com.restsecure.core.response;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;

import java.util.List;

public interface MutableResponse extends Response {

    /**
     * Setting the body in response
     *
     * @param body ResponseBody
     */
    void setBody(ResponseBody body);

    /**
     * Setting the headers in response
     *
     * @param headers response headers
     */
    void setHeaders(List<Header> headers);

    /**
     * Setting the cookies in response
     *
     * @param cookies response cookies
     */
    void setCookies(List<Cookie> cookies);

    /**
     * Setting the response status line
     *
     * @param statusLine response status line
     */
    void setStatusLine(String statusLine);

    /**
     * Setting the status code in response
     *
     * @param statusCode response status code
     */
    void setStatusCode(int statusCode);

    /**
     * Set the response time in milliseconds
     *
     * @param time response time
     */
    void setTime(long time);
}
