package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.events.CommentEvents;
import com.squareup.otto.Bus;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by emma on 28/03/15.
 */
public class OnCommentNextListener implements Action1<List<Comment>> {
    private Bus mNetworkBus;

    public OnCommentNextListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(List<Comment> comments) {
        mNetworkBus.post(new CommentEvents.RequestSucceededEvent(comments));
    }
}
