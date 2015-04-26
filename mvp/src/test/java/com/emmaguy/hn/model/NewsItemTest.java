package com.emmaguy.hn.model;

import com.google.gson.Gson;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemTest {

    @Test
    public void test_newsItemDeserialisation() {
        NewsItem item = new Gson().fromJson(getNewsItemJson(), NewsItem.class);

        assertThat(item.getId(), equalTo("1234"));
        assertThat(item.getScore(), equalTo(111));
        assertThat(item.getTitle(), equalTo("Emma is awesome"));
        assertThat(item.getUrl(), equalTo("http://www.github.com/emmaguy"));
        assertThat(item.getRootCommentIds(), contains("8952", "9224"));
        assertThat(item.getAuthor(), equalTo("emma"));
        assertThat(item.getTime(), equalTo(1175714200l));
    }

    private String getNewsItemJson() {
        return "{\n" +
                "  \"by\" : \"emma\",\n" +
                "  \"descendants\" : 71,\n" +
                "  \"id\" : 1234,\n" +
                "  \"kids\" : [ 8952, 9224 ],\n" +
                "  \"score\" : 111,\n" +
                "  \"time\" : 1175714200,\n" +
                "  \"title\" : \"Emma is awesome\",\n" +
                "  \"type\" : \"story\",\n" +
                "  \"url\" : \"http://www.github.com/emmaguy\"\n" +
                "}";
    }

    @Test
    public void test_settingPermalink_appendsId() {
        NewsItem item = new NewsItem("123");
        item.setPermalink("http://www.emmaisawesome.com/item?id=");

        assertThat(item.getPermalink(), equalTo("http://www.emmaisawesome.com/item?id=123"));
    }
}
