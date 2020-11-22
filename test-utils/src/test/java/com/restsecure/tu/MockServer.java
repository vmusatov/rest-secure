package com.restsecure.tu;

import com.restsecure.core.http.header.Header;
import com.restsecure.core.mapping.serialize.DefaultJacksonSerializer;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import org.eclipse.jetty.util.UrlEncoded;
import spark.Route;

import javax.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class MockServer {
    public static final int PORT = 8081;
    public static final String HOST = "http://localhost:" + PORT;

    private static final String EMPTY_RESPONSE = "Empty response";

    private static String responseBody = EMPTY_RESPONSE;
    private static List<Cookie> responseCookies = new ArrayList<>();
    private static List<Header> responseHeaders = new ArrayList<>();

    private static final DefaultJacksonSerializer serializer = new DefaultJacksonSerializer();

    public static void reset() {
        responseBody = EMPTY_RESPONSE;
        responseCookies.clear();
        responseHeaders.clear();
    }

    public static void addResponseHeader(String name, String value) {
        responseHeaders.add(new Header(name, value));
    }

    public static void addResponseCookie(Cookie cookie) {
        responseCookies.add(cookie);
    }

    public static void addResponseCookie(String name, String value) {
        responseCookies.add(new Cookie(name, UrlEncoded.encodeString(value)));
    }

    public static void setResponseBody(Object response) {
        if (SerializeHelper.isNeedSerialize(response)) {
            responseBody = serializer.serialize(response);
        } else {
            responseBody = String.valueOf(response);
        }
    }

    private static Route generateResponse() {
        return ((request, response) -> {
            responseCookies.forEach(c -> response.raw().addCookie(c));
            responseHeaders.forEach(h -> response.header(h.getName(), h.getValue()));

            return responseBody;
        });
    }

    public static void main(String[] args) {
        port(PORT);

        get("/get", generateResponse());
        post("/post", generateResponse());
        put("/put", generateResponse());
        delete("/delete", generateResponse());
    }
}
