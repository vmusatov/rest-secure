package com.restsecure.core.util;

import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class MultiKeyMapTest {

    @Test
    public void putTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        assertThat(mkp.isEmpty(), is(true));

        mkp.put("name", "value");
        assertThat(mkp.size(), is(1));
        assertThat(mkp.getFirst("name"), is("value"));
    }

    @Test
    public void getFirstTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        mkp.put("name1", "value1");
        mkp.put("name2", "value2");
        mkp.put("name1", "value3");
        mkp.put("name4", "value4");

        assertThat(mkp.getFirst("name1"), is("value1"));
    }

    @Test
    public void getLastTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        mkp.put("name1", "value1");
        mkp.put("name2", "value2");
        mkp.put("name1", "value3");
        mkp.put("name4", "value4");

        assertThat(mkp.getLast("name1"), is("value3"));
    }

    @Test
    public void getAllTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        mkp.put("name1", "value1");
        mkp.put("name2", "value2");
        mkp.put("name1", "value3");
        mkp.put("name4", "value4");

        List<String> values = mkp.getAll("name1");

        assertThat(values.size(), is(2));
        assertThat(values, containsInAnyOrder("value1", "value3"));
    }

    @Test
    public void containsKeyTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        assertThat(mkp.containsKey("name"), is(false));

        mkp.put("name", "value");
        assertThat(mkp.containsKey("name"), is(true));
    }

    @Test
    public void deleteAllWithKeyTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        mkp.put("name1", "value1");
        mkp.put("name2", "value2");
        mkp.put("name1", "value3");
        mkp.put("name4", "value4");

        mkp.deleteAllWithKey("name1");

        assertThat(mkp.size(), is(2));
        assertThat(mkp.containsKey("name2"), is(true));
        assertThat(mkp.containsKey("name4"), is(true));
    }

    @Test
    public void isEmptyTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        assertThat(mkp.isEmpty(), is(true));

        mkp.put("name", "value");
        assertThat(mkp.isEmpty(), is(false));
    }

    @Test
    public void sizeTest() {
        MultiKeyMap<String, String> mkp = new MultiKeyMap<>();
        assertThat(mkp.size(), is(0));

        mkp.put("name", "value");
        assertThat(mkp.size(), is(1));

        mkp.put("name1", "value1");
        assertThat(mkp.size(), is(2));
    }
}
