package com.restsecure.session;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SessionConfigTest {

    @Test
    public void setSessionIdNameTest() {
        SessionConfig config = new SessionConfig();
        config.setSessionIdName("PHPSESSID");

        assertThat(config.getSessionIdName(), equalTo("PHPSESSID"));
    }
}
