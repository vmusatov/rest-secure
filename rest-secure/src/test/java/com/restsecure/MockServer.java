package com.restsecure;

import com.restsecure.core.http.header.Header;
import com.restsecure.core.mapping.serialize.DefaultJacksonSerializer;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import org.eclipse.jetty.util.UrlEncoded;
import spark.Route;
import spark.Spark;

import javax.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

public class MockServer {
    private static final String EMPTY_RESPONSE = "Empty response";

    public static final int PORT = 8081;
    public static final String HOST = "http://localhost:" + PORT;
    public static final String GET_PATH = HOST + "/get";
    public static final String POST_PATH = HOST + "/post";
    public static final String PUT_PATH = HOST + "/put";
    public static final String DELETE_PATH = HOST + "/delete";
    public static final String HEAD_PATH = HOST + "/head";
    public static final String TRACE_PATH = HOST + "/trace";
    public static final String OPTIONS_PATH = HOST + "/options";
    public static final String PATCH_PATH = HOST + "/patch";

    public static int requestCount;

    private static String responseBody = EMPTY_RESPONSE;
    private static List<Cookie> responseCookies = new ArrayList<>();
    private static List<Header> responseHeaders = new ArrayList<>();

    private static final DefaultJacksonSerializer serializer = new DefaultJacksonSerializer();

    static {
        Spark.port(PORT);
        Spark.before((a, b) -> requestCount++);
        Spark.get("/get", generateResponse());
        Spark.post("/post", generateResponse());
        Spark.put("/put", generateResponse());
        Spark.delete("/delete", generateResponse());
        Spark.head("/head", generateResponse());
        Spark.trace("/trace", generateResponse());
        Spark.options("/options", generateResponse());
        Spark.patch("/patch", generateResponse());
    }

    public static void reset() {
        requestCount = 0;
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
        return (request, response) -> {
            responseCookies.forEach(c -> response.raw().addCookie(c));
            responseHeaders.forEach(h -> response.header(h.getName(), h.getValue()));

            return responseBody;
        };
    }

    public static void main(String[] args) {
    }
}
