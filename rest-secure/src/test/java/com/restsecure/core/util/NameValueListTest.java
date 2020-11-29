package com.restsecure.core.util;

import com.restsecure.core.http.header.Header;
import com.restsecure.core.util.NameValueList;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NameValueListTest {

    private List<Header> headersList = new ArrayList<>();

    @BeforeClass
    public void init() {
        headersList.add(new Header("name1", "value1"));
        headersList.add(new Header("repeatName", "value2"));
        headersList.add(new Header("name3", "value3"));
        headersList.add(new Header("repeatName", "value4"));
        headersList.add(new Header("name4", "100"));
        headersList.add(new Header("name4", "200"));
    }

    @Test
    public void constructorWithListTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);

        assertThat(list.size(), equalTo(headersList.size()));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void constructorWithMultiValueListTest() {
        NameValueList<Header> headersNameValueList = new NameValueList<>(headersList);
        NameValueList<Header> list = new NameValueList<>(headersNameValueList);

        assertThat(list.size(), equalTo(headersList.size()));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void emptyConstructorTest() {
        NameValueList<Header> list = new NameValueList<>();
        assertThat(list.size(), equalTo(0));
    }

    @Test
    public void isEmptyTest() {
        NameValueList<Header> list = new NameValueList<>();
        assertThat(list.isEmpty(), equalTo(true));

        list.add(new Header("name", "value"));
        assertThat(list.isEmpty(), equalTo(false));
    }

    @Test
    public void addTest() {
        NameValueList<Header> list = new NameValueList<>();
        list.add(new Header("name1", "value1"));

        assertThat(list.size(), equalTo(1));
        assertThat(list.getFirst("name1").getValue(), equalTo("value1"));

        list.add(new Header("name2", "value2"));

        assertThat(list.size(), equalTo(2));
        assertThat(list.getFirst("name2").getValue(), equalTo("value2"));
    }

    @Test
    public void addAllWithListTest() {
        NameValueList<Header> list = new NameValueList<>();
        list.addAll(headersList);

        assertThat(list.size(), equalTo(headersList.size()));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void addAllWithMultiValueListTest() {
        NameValueList<Header> headersNameValueList = new NameValueList<>(headersList);
        NameValueList<Header> list = new NameValueList<>();
        list.addAll(headersNameValueList);

        assertThat(list.size(), equalTo(headersNameValueList.size()));
        assertThat(list.getFirst("repeatName").getValue(), equalTo("value2"));
    }

    @Test
    public void containsTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        Header header = new Header("headerName", "headerValue");
        Header header2 = new Header("headerName2", "headerValue2");

        assertThat(list.contains(header), equalTo(false));

        list.add(header);

        assertThat(list.contains(header), equalTo(true));
        assertThat(list.contains(header2), equalTo(false));
    }

    @Test
    public void containsAllTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
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
        NameValueList<Header> list = new NameValueList<>(headersList);
        Header expectedHeader1 = new Header("name1", "value1");
        Header expectedHeader2 = new Header("name3", "value3");

        assertThat(list.get(0), equalTo(expectedHeader1));
        assertThat(list.get(2), equalTo(expectedHeader2));
    }

    @Test
    public void getFirstValueTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);

        assertThat(list.getFirstValue("name1"), equalTo("value1"));
        assertThat(list.getFirstValue("name3"), equalTo("value3"));
    }

    @Test
    public void getFirstValueAsIntTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        assertThat(list.getFirstValue("name4", Integer::parseInt), equalTo(100));
    }

    @Test
    public void getLastValueTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);

        assertThat(list.getLastValue("repeatName"), equalTo("value4"));
        assertThat(list.getLastValue("name3"), equalTo("value3"));
    }

    @Test
    public void getLastValueAsIntTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        assertThat(list.getLastValue("name4", Integer::parseInt), equalTo(200));
    }

    @Test
    public void getAllValuesTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
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
    public void getAllValuesAsIntTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        List<Integer> valuesList = list.getAllValues("name4", Integer::parseInt);

        assertThat(valuesList.size(), equalTo(2));
        assertThat(valuesList.contains(100), equalTo(true));
        assertThat(valuesList.contains(200), equalTo(true));

        list.add(new Header("name4", "300"));
        valuesList = list.getAllValues("name4", Integer::parseInt);

        assertThat(valuesList.size(), equalTo(3));
        assertThat(valuesList.contains(300), equalTo(true));
    }

    @Test
    public void getFirstTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        Header expectedHeader = new Header("repeatName", "value2");

        assertThat(list.getFirst("repeatName"), equalTo(expectedHeader));
        assertThat(list.getFirst("wrongName"), equalTo(null));
    }

    @Test
    public void getLastTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        Header expectedHeader = new Header("repeatName", "value4");

        assertThat(list.getLast("repeatName"), equalTo(expectedHeader));
        assertThat(list.getLast("wrongName"), equalTo(null));
    }

    @Test
    public void getAllTest() {
        NameValueList<Header> list = new NameValueList<>(headersList);
        Header expectedHeader1 = new Header("repeatName", "value2");
        Header expectedHeader2 = new Header("repeatName", "value4");
        Header expectedHeader3 = new Header("repeatName", "value5");

        NameValueList<Header> allList = list.getAll("repeatName");

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
