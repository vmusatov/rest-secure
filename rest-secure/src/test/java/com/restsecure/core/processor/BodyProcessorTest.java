package com.restsecure.core.processor;

import com.restsecure.BaseTest;
import com.restsecure.TestObjectMapper;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import org.testng.annotations.Test;

import static com.restsecure.Configs.objectMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BodyProcessorTest {

    @Test
    public void processContextWithNoSerializationTest() {
        RequestSpecification spec = new RequestSpecificationImpl().body(1);
        RequestContext context = new RequestContext(spec);
        BodyProcessor processor = new BodyProcessor();

        processor.processRequest(context);
        assertThat(context.getSpecification().getBody(), equalTo(1));
    }

    @Test
    public void processContextWithSerializationTest() {
        BaseTest.Phone phone = new BaseTest.Phone();
        phone.setCode("+7");
        phone.setNumber("7777777777");

        RequestSpecification spec = new RequestSpecificationImpl()
                .body(phone)
                .config(objectMapper(new TestObjectMapper()));

        RequestContext context = new RequestContext(spec);
        BodyProcessor processor = new BodyProcessor();

        processor.processRequest(context);
        assertThat(context.getSpecification().getBody(), equalTo("{\"code\":\"+7\",\"number\":\"7777777777\"}"));
    }
}
