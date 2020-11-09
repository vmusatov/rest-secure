package com.restsecure.session;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SessionConfigTest {

    @Test
    public void setSessionIdNameTest() {
        SessionIdNameConfig config = new SessionIdNameConfig();
        config.setValue("PHPSESSID");
        assertThat(config.getValue(), equalTo("PHPSESSID"));
    }
}
