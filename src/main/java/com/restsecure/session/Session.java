package com.restsecure.session;

import com.restsecure.configuration.ConfigFactory;
import com.restsecure.http.Cookie;
import com.restsecure.http.MultiValueList;
import com.restsecure.request.handler.RequestHandler;
import com.restsecure.request.RequestSender;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.Response;
import com.restsecure.response.handler.ResponseHandler;
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
    private String sessionIdValue;

    @Override
    public void handleRequest(RequestSpecification spec) {
        SessionConfig sessionConfig = ConfigFactory.getConfigOrCreateDefault(spec.getConfigs(), SessionConfig.class);
        String sessionIdName = sessionConfig.getSessionIdName();

        if (sessionIdValue != null && !sessionIdValue.isEmpty()) {
            spec.header("Cookie", sessionIdName + "=" + sessionIdValue);
        }
    }

    @Override
    public void handleResponse(Response response, RequestSpecification spec) {
        MultiValueList<Cookie> responseCookies = response.getCookies();
        if (responseCookies == null) {
            return;
        }

        SessionConfig sessionConfig = ConfigFactory.getConfigOrCreateDefault(spec.getConfigs(), SessionConfig.class);

        Cookie sessionCookie = responseCookies.getFirst(sessionConfig.getSessionIdName());
        if (sessionCookie != null) {
            sessionIdValue = sessionCookie.getValue();
        }
    }
}
