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
     * @return MultiValueList<Header>
     */
    NameValueList<Header> getHeaders();

    /**
     * @return MultiValueList<Cookie>
     */
    NameValueList<Cookie> getCookies();

    /**
     * @return response status line
     */
    String getStatusLine();

    /**
     * @return status code
     */
    int getStatusCode();

    /**
     * @return response time in milliseconds
     */
    long getTime();

    /**
     * @param timeUnit time unit
     * @return response time in specify time unit
     */
    long getTimeIn(TimeUnit timeUnit);
}
