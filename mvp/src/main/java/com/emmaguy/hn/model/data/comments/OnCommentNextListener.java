package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.events.CommentEvents;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by emma on 28/03/15.
 */
public class OnCommentNextListener implements Action1<List<Comment>> {
    private RxBus mNetworkBus;

    public OnCommentNextListener(RxBus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(List<Comment> comments) {
        mNetworkBus.send(new CommentEvents.RequestSucceededEvent(comments));
    }
}
