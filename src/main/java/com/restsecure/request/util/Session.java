package com.restsecure.request.util;

import com.restsecure.RestSecureConfiguration;
import com.restsecure.http.Cookie;
import com.restsecure.http.MultiValueList;
import com.restsecure.request.RequestHandler;
import com.restsecure.request.RequestSender;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseHandler;
import lombok.Getter;

/**
 * The session class will help you get session ID returned by server and use it for following requests.<br>
 * For example:
 * <pre>
 *     Session session = new Session();
 *
 *     get("url")
 *          .session(session)
 *     .send();
 *
 *     get("other_url")
 *          .session(session)
 *     .send();
 * </pre>
 * In the second request the session instance is reused for automatic setting of the session id.<br><br>
 * For easier installation of an session for several requests use the {@link RequestSender}<br>
 * For example:
 * <pre>
 *     Session session = new Session();
 *     RequestSpecification one = get("url);
 *     RequestSpecification two = get("other_url);
 *
 *     RequestSender.send(
 *          session,
 *          one,
 *          two
 *     );
 * </pre>
 */
public class Session implements RequestHandler, ResponseHandler {

    @Getter
    private String sessionValue;

    @Override
    public void handleRequest(RequestSpecification spec) {
        if (sessionValue != null && !sessionValue.isEmpty()) {
            spec.header("Cookie", RestSecureConfiguration.getSessionId() + "=" + sessionValue);
        }
    }

    @Override
    public void handleResponse(Response response) {
        MultiValueList<Cookie> responseCookies = response.getCookies();
        if (responseCookies == null) {
            return;
        }

        Cookie sessionCookie = responseCookies.getFirst(RestSecureConfiguration.getSessionId());
        if (sessionCookie != null) {
            sessionValue = sessionCookie.getValue();
        }
    }
}
