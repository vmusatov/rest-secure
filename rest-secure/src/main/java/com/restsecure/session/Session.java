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
 *     RequestSpec one = get("url);
 *     RequestSpec two = get("other_url);
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
        String sessionIdName = context.getConfigValue(SessionIdNameConfig.class);

        if (sessionIdValue != null && !sessionIdValue.isEmpty()) {
            context.getRequestSpec().header("Cookie", sessionIdName + "=" + sessionIdValue);
        }
    }

    @Override
    public void processResponse(RequestContext context, Response response) {
        NameValueList<Cookie> responseCookies = response.getCookies();
        if (responseCookies == null) {
            return;
        }

        String sessionIdName = context.getConfigValue(SessionIdNameConfig.class);

        Cookie sessionCookie = responseCookies.getFirst(sessionIdName);
        if (sessionCookie != null) {
            sessionIdValue = sessionCookie.getValue();
        }
    }
}
