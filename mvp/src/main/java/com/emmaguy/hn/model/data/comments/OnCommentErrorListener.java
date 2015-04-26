package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.model.data.events.CommentEvents;
import com.squareup.otto.Bus;

import rx.functions.Action1;

/**
 * Created by emma on 28/03/15.
 */
public class OnCommentErrorListener implements Action1<Throwable> {
    private Bus mNetworkBus;

    public OnCommentErrorListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(Throwable throwable) {
        mNetworkBus.post(new CommentEvents.RequestFailedEvent());
    }
}
