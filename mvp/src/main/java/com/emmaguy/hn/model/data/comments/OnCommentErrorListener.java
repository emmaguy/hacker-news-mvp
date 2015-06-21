package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.data.events.CommentEvents;

import rx.functions.Action1;

/**
 * Created by emma on 28/03/15.
 */
public class OnCommentErrorListener implements Action1<Throwable> {
    private RxBus mNetworkBus;

    public OnCommentErrorListener(RxBus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(Throwable throwable) {
        mNetworkBus.send(new CommentEvents.RequestFailedEvent());
    }
}
