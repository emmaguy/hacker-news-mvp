package com.emmaguy.hn.model;

import com.google.gson.Gson;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

/**
 * Created by emma on 21/03/15.
 */
public class CommentTest {

    @Test
    public void commentDeserialisation() {
        Comment item = new Gson().fromJson(getCommentJson(), Comment.class);

        assertThat(item.getId(), equalTo("8952"));
        assertThat(item.getText(), equalTo("Blah blah"));
        assertThat(item.getParent(), equalTo("8863"));
        assertThat(item.getChildCommentIds(), contains("9153"));
    }

    private String getCommentJson() {
        return "{\n" +
                "by: \"emma\",\n" +
                "id: 8952,\n" +
                "kids: [\n" +
                "9153\n" +
                "],\n" +
                "parent: 8863,\n" +
                "text: \"Blah blah\",\n" +
                "time: 1175727286,\n" +
                "type: \"comment\"\n" +
                "}";
    }
}
