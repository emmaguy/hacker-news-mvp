package com.emmaguy.hn.model.data.events;

import com.emmaguy.hn.model.Comment;

import java.util.List;

/**
 * Created by emma on 26/04/15.
 */
public class CommentEvents {
    public static class RequestSucceededEvent {
        private final List<Comment> mComments;

        public RequestSucceededEvent(List<Comment> comments) {
            mComments = comments;
        }

        public List<Comment> getComments() {
            return mComments;
        }
    }

    public static class RequestFailedEvent {
    }
}
