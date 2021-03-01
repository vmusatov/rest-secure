package com.restsecure.core.response;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.NameValueList;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface Response {

    /**
     * @return ResponseBody
     */
    ResponseBody getBody();

    /**
     * Setting the body in response
     *
     * @param body ResponseBody
     */
    void setBody(ResponseBody body);

    /**
     * @return MultiValueList<Header>
     */
    NameValueList<Header> getHeaders();

    /**
     * Setting the headers in response
     *
     * @param headers response headers
     */
    void setHeaders(List<Header> headers);

    /**
     * @return MultiValueList<Cookie>
     */
    NameValueList<Cookie> getCookies();

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
     * @return response status line
     */
    String getStatusLine();

    /**
     * @return status code
     */
    int getStatusCode();

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

    /**
     * @return response time in milliseconds
     */
    long getTime();

    /**
     * @param timeUnit time unit
     * @return response time in specify time unit
     */
    long getTimeIn(TimeUnit timeUnit);

    /**
     * Set request context
     * @param context RequestContext
     */
    void setContext(RequestContext context);

    /**
     * @return RequestContext
     */
    RequestContext getContext();
}
