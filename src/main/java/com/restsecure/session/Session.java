package com.restsecure.session;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.response.Response;
import com.restsecure.core.util.NameValueList;
import lombok.Getter;

/**
 * The session class will help you get session ID returned by server and use it for following requests.<br>
 * For example:
 * <pre>
 *     Session session = new Session();
 *
 *     get("url")
 *          .process(session)
 *     .send();
 *
 *     get("other_url")
 *          .process(session)
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
public class Session implements Processor {

    @Getter
    private String sessionIdValue;

    @Override
    public void processRequest(RequestContext context) {
        SessionConfig sessionConfig = context.getConfig(SessionConfig.class);
        String sessionIdName = sessionConfig.getSessionIdName();

        if (sessionIdValue != null && !sessionIdValue.isEmpty()) {
            context.getSpecification().header("Cookie", sessionIdName + "=" + sessionIdValue);
        }
    }

    @Override
    public void processResponse(RequestContext context, Response response) {
        NameValueList<Cookie> responseCookies = response.getCookies();
        if (responseCookies == null) {
            return;
        }

        SessionConfig sessionConfig = context.getConfig(SessionConfig.class);

        Cookie sessionCookie = responseCookies.getFirst(sessionConfig.getSessionIdName());
        if (sessionCookie != null) {
            sessionIdValue = sessionCookie.getValue();
        }
    }
}
