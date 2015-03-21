package com.emmaguy.hn.model;

import com.google.gson.Gson;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemTest {

    @Test
    public void test_newsItemDeserialisation() {
        NewsItem item = new Gson().fromJson(getNewsItemJson(), NewsItem.class);

        assertThat(item.getTitle(), equalTo("Emma is awesome"));
        assertThat(item.getUrl(), equalTo("http://www.github.com/emmaguy"));
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
}
