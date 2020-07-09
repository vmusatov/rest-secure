package com.restsecure.core.response;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.MultiValueList;

import java.util.List;

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
    MultiValueList<Header> getHeaders();

    /**
     * Setting the headers in response
     *
     * @param headers response headers
     */
    void setHeaders(List<Header> headers);

    /**
     * @return MultiValueList<Cookie>
     */
    MultiValueList<Cookie> getCookies();

    /**
     * Setting the cookies in response
     *
     * @param cookies response cookies
     */
    void setCookies(List<Cookie> cookies);

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
}
