package com.restsecure.core.request;

import com.restsecure.BaseTest;
import com.restsecure.MockServer;
import com.restsecure.RestSecure;
import com.restsecure.core.condition.ContextCondition;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.StatusCode;
import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import com.restsecure.validation.BaseValidation;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Validations.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestSenderTest extends BaseTest {

    private StatusCode expectStatusCode = StatusCode.OK;
    private String expectStatusLine = "HTTP/1.1 200 OK";
    private String expectBody = "some body";
    private Header expectHeader = new Header("header", "header_value");
    private Cookie expectCookie = new Cookie("cookie", "cookie_value");

    @BeforeClass
    public void setupGlobalSpec() {
        RestSecure.getGlobalSpecification().expect(checkResponse());
    }

    @BeforeMethod
    public void setup() {
        MockServer.reset();
        MockServer.addResponseCookie(expectCookie.getName(), expectCookie.getValue());
        MockServer.addResponseHeader(expectHeader.getName(), expectHeader.getValue());
        MockServer.setResponseBody(expectBody);
    }

    @AfterClass
    public void teardown() {
        MockServer.reset();
        RestSecure.resetGlobalSpec();
    }

    @Test()
    public void sendOneRequestTest() {
        RequestSender.send(RestSecure.get(MockServer.GET_PATH));
        RequestSender.send(RestSecure.post(MockServer.POST_PATH));
        RequestSender.send(RestSecure.put(MockServer.PUT_PATH));
        RequestSender.send(RestSecure.delete(MockServer.DELETE_PATH));
        RequestSender.send(RestSecure.head(MockServer.HEAD_PATH));
        RequestSender.send(RestSecure.trace(MockServer.TRACE_PATH));
        RequestSender.send(RestSecure.options(MockServer.OPTIONS_PATH));
        RequestSender.send(RestSecure.patch(MockServer.PATCH_PATH));

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendMultipleRequestsTest() {
        RequestSender.send(
                RestSecure.get(MockServer.GET_PATH),
                RestSecure.post(MockServer.POST_PATH),
                RestSecure.put(MockServer.PUT_PATH),
                RestSecure.delete(MockServer.DELETE_PATH),
                RestSecure.head(MockServer.HEAD_PATH),
                RestSecure.trace(MockServer.TRACE_PATH),
                RestSecure.options(MockServer.OPTIONS_PATH),
                RestSecure.patch(MockServer.PATCH_PATH)
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendRequestsListTest() {
        List<RequestSpecification> requests = Arrays.asList(
                RestSecure.get(MockServer.GET_PATH),
                RestSecure.post(MockServer.POST_PATH),
                RestSecure.put(MockServer.PUT_PATH),
                RestSecure.delete(MockServer.DELETE_PATH),
                RestSecure.head(MockServer.HEAD_PATH),
                RestSecure.trace(MockServer.TRACE_PATH),
                RestSecure.options(MockServer.OPTIONS_PATH),
                RestSecure.patch(MockServer.PATCH_PATH)
        );

        RequestSender.send(requests);

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendOneRequestWithOneProcessorTest() {

        RequestSender.send(
                new TestProcessor(),
                RestSecure.get(MockServer.GET_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.post(MockServer.POST_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.put(MockServer.PUT_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.delete(MockServer.DELETE_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.head(MockServer.HEAD_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.trace(MockServer.TRACE_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(isUseTestProcessor(1))
        );
        RequestSender.send(
                new TestProcessor(),
                RestSecure.patch(MockServer.PATCH_PATH).expect(isUseTestProcessor(1))
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendOneRequestWithProcessorsListTest() {
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.get(MockServer.GET_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.post(MockServer.POST_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.put(MockServer.PUT_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.delete(MockServer.DELETE_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.head(MockServer.HEAD_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.trace(MockServer.TRACE_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(isUseTestProcessor(3))
        );
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.patch(MockServer.PATCH_PATH).expect(isUseTestProcessor(3))
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendMultipleRequestsWithOneProcessorTest() {
        RequestSender.send(
                new TestProcessor(),
                RestSecure.get(MockServer.GET_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.post(MockServer.POST_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.put(MockServer.PUT_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.delete(MockServer.DELETE_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.head(MockServer.HEAD_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.trace(MockServer.TRACE_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.patch(MockServer.PATCH_PATH).expect(isUseTestProcessor(1), teardownTestProcessors)
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendMultipleRequestsWithProcessorsListTest() {
        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                RestSecure.get(MockServer.GET_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.post(MockServer.POST_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.put(MockServer.PUT_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.delete(MockServer.DELETE_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.head(MockServer.HEAD_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.trace(MockServer.TRACE_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.patch(MockServer.PATCH_PATH).expect(isUseTestProcessor(3), teardownTestProcessors)
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendRequestsListWithOneProcessorTest() {
        List<RequestSpecification> requests = Arrays.asList(
                RestSecure.get(MockServer.GET_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.post(MockServer.POST_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.put(MockServer.PUT_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.delete(MockServer.DELETE_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.head(MockServer.HEAD_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.trace(MockServer.TRACE_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(isUseTestProcessor(1), teardownTestProcessors),
                RestSecure.patch(MockServer.PATCH_PATH).expect(isUseTestProcessor(1), teardownTestProcessors)
        );

        RequestSender.send(new TestProcessor(), requests);

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendRequestsListWithProcessorsListTest() {
        List<RequestSpecification> requests = Arrays.asList(
                RestSecure.get(MockServer.GET_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.post(MockServer.POST_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.put(MockServer.PUT_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.delete(MockServer.DELETE_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.head(MockServer.HEAD_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.trace(MockServer.TRACE_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(isUseTestProcessor(3), teardownTestProcessors),
                RestSecure.patch(MockServer.PATCH_PATH).expect(isUseTestProcessor(3), teardownTestProcessors)
        );

        RequestSender.send(
                Arrays.asList(new TestProcessor(), new TestProcessor(), new TestProcessor()),
                requests
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    private Validation teardownTestProcessors = new BaseValidation() {
        @Override
        public ValidationResult softValidate(Response response) {
            RequestContext context = response.getContext();
            List<Processor> processors = context.getSpecification().getProcessors();

            for (Processor processor : processors) {
                if (processor instanceof TestProcessor) {
                    TestProcessor testProcessor = (TestProcessor) processor;
                    testProcessor.isProcessRequest = false;
                    testProcessor.isProcessResponse = false;
                }
            }

            return new ValidationResult(ValidationStatus.SUCCESS);
        }
    };

    private Validation isUseTestProcessor(int processorsCount) {
        return new BaseValidation() {
            @Override
            public ValidationResult softValidate(Response response) {
                RequestContext context = response.getContext();
                List<Processor> processors = context.getSpecification().getProcessors();

                int count = 0;

                for (Processor processor : processors) {
                    if (processor instanceof TestProcessor) {
                        count++;
                        TestProcessor testProcessor = (TestProcessor) processor;

                        if (!testProcessor.isProcessRequest) {
                            return new ValidationResult(ValidationStatus.FAIL, "Processor not process request");
                        }

                        if (!testProcessor.isProcessResponse) {
                            return new ValidationResult(ValidationStatus.FAIL, "Processor not process response");
                        }
                    }
                }

                if (count == processorsCount) {
                    return new ValidationResult(ValidationStatus.SUCCESS);
                } else {
                    return new ValidationResult(ValidationStatus.FAIL, "Processor not use");
                }
            }
        };
    }

    private Validation checkResponse() {
        ContextCondition needCheckBody = ctx -> ctx.getSpecification().getMethod() != RequestMethod.HEAD;

        return combine(
                statusCode(expectStatusCode),
                statusLine(expectStatusLine),
                headers(containsPair(expectHeader)),
                cookies(containsPair(expectCookie)),
                when(needCheckBody, then(
                        body(expectBody)
                ))
        );
    }

    private class TestProcessor implements Processor {
        private boolean isProcessRequest = false;
        private boolean isProcessResponse = false;

        @Override
        public void processRequest(RequestContext context) {
            this.isProcessRequest = true;
        }

        @Override
        public void processResponse(Response response) {
            this.isProcessResponse = true;
        }
    }
}
