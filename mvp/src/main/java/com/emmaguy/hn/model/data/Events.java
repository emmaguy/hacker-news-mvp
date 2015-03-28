package com.emmaguy.hn.model.data;

import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.NewsItem;

import java.util.List;

/**
 * Created by emma on 28/03/15.
 */
public class Events {
    public static class NewsItemsSuccessEvent extends EventWithData<List<NewsItem>> {
        public NewsItemsSuccessEvent(List<NewsItem> data) {
            super(data);
        }
    }

    public static class CommentsSuccessEvent extends EventWithData<List<Comment>> {
        public CommentsSuccessEvent(List<Comment> data) {
            super(data);
        }
    }

    public static class NewsItemsRequestFailedEvent {
    }

    public static class CommentRequestFailedEvent {
    }

    public static abstract class EventWithData<T> {
        protected final T mData;

        protected EventWithData(T data) {
            mData = data;
        }

        public T getData() {
            return mData;
        }
    }
}
