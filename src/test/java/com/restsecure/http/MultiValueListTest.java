package com.restsecure.http;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MultiValueListTest {

    private List<Header> headersList = new ArrayList<>();

    @BeforeClass
    public void init() {
        headersList.add(new Header("name1", "value1"));
        headersList.add(new Header("repeatName", "value2"));
        headersList.add(new Header("name3", "value3"));
        headersList.add(new Header("repeatName", "value4"));
    }

    @Test
    public void constructorWithListTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);

        assertThat(list.size(), equalTo(4));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void constructorWithMultiValueListTest() {
        MultiValueList<Header> headersMultiValueList = new MultiValueList<>(headersList);
        MultiValueList<Header> list = new MultiValueList<>(headersMultiValueList);

        assertThat(list.size(), equalTo(4));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void emptyConstructorTest() {
        MultiValueList<Header> list = new MultiValueList<>();
        assertThat(list.size(), equalTo(0));
    }

    @Test
    public void isEmptyTest() {
        MultiValueList<Header> list = new MultiValueList<>();
        assertThat(list.isEmpty(), equalTo(true));

        list.add(new Header("name", "value"));
        assertThat(list.isEmpty(), equalTo(false));
    }

    @Test
    public void addTest() {
        MultiValueList<Header> list = new MultiValueList<>();
        list.add(new Header("name1", "value1"));

        assertThat(list.size(), equalTo(1));
        assertThat(list.getFirst("name1").getValue(), equalTo("value1"));

        list.add(new Header("name2", "value2"));

        assertThat(list.size(), equalTo(2));
        assertThat(list.getFirst("name2").getValue(), equalTo("value2"));
    }

    @Test
    public void addAllWithListTest() {
        MultiValueList<Header> list = new MultiValueList<>();
        list.addAll(headersList);

        assertThat(list.size(), equalTo(4));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void addAllWithMultiValueListTest() {
        MultiValueList<Header> headersMultiValueList = new MultiValueList<>(headersList);
        MultiValueList<Header> list = new MultiValueList<>();
        list.addAll(headersList);

        assertThat(list.size(), equalTo(4));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void containsTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);
        Header header = new Header("headerName", "headerValue");
        Header header2 = new Header("headerName2", "headerValue2");

        assertThat(list.contains(header), equalTo(false));

        list.add(header);

        assertThat(list.contains(header), equalTo(true));
        assertThat(list.contains(header2), equalTo(false));
    }

    @Test
    public void containsAllTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);
        Header header = new Header("headerName", "headerValue");
        Header header2 = new Header("headerName2", "headerValue2");

        assertThat(list.containsAll(Arrays.asList(header, header2)), equalTo(false));
        assertThat(list.containsAll(Arrays.asList(header2, header)), equalTo(false));

        list.add(header);
        list.add(header2);

        assertThat(list.containsAll(Arrays.asList(header, header2)), equalTo(true));
        assertThat(list.containsAll(Arrays.asList(header2, header)), equalTo(true));
    }

    @Test
    public void getTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);
        Header expectedHeader1 = new Header("name1", "value1");
        Header expectedHeader2 = new Header("name3", "value3");

        assertThat(list.get(0), equalTo(expectedHeader1));
        assertThat(list.get(2), equalTo(expectedHeader2));
    }

    @Test
    public void getValueTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);

        assertThat(list.getValue("name1"), equalTo("value1"));
        assertThat(list.getValue("name3"), equalTo("value3"));
    }

    @Test
    public void getAllValuesTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);
        List<String> valuesList = list.getAllValues("repeatName");

        assertThat(valuesList.size(), equalTo(2));
        assertThat(valuesList.contains("value2"), equalTo(true));
        assertThat(valuesList.contains("value4"), equalTo(true));

        list.add(new Header("repeatName", "value5"));
        valuesList = list.getAllValues("repeatName");

        assertThat(valuesList.size(), equalTo(3));
        assertThat(valuesList.contains("value5"), equalTo(true));
    }

    @Test
    public void getFirstTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);
        Header expectedHeader = new Header("repeatName", "value2");

        assertThat(list.getFirst("repeatName"), equalTo(expectedHeader));
        assertThat(list.getFirst("wrongName"), equalTo(null));
    }

    @Test
    public void getAllTest() {
        MultiValueList<Header> list = new MultiValueList<>(headersList);
        Header expectedHeader1 = new Header("repeatName", "value2");
        Header expectedHeader2 = new Header("repeatName", "value4");
        Header expectedHeader3 = new Header("repeatName", "value5");

        MultiValueList<Header> allList = list.getAll("repeatName");

        MatcherAssert.assertThat(allList.size(), equalTo(2));
        MatcherAssert.assertThat(allList.contains(expectedHeader1), equalTo(true));
        MatcherAssert.assertThat(allList.contains(expectedHeader2), equalTo(true));

        list.add(expectedHeader3);
        allList = list.getAll("repeatName");

        MatcherAssert.assertThat(allList.size(), equalTo(3));
        MatcherAssert.assertThat(allList.contains(expectedHeader1), equalTo(true));
        MatcherAssert.assertThat(allList.contains(expectedHeader2), equalTo(true));
        MatcherAssert.assertThat(allList.contains(expectedHeader3), equalTo(true));
    }
}
